/**
 * Copyright (C) 2022 zProtect
 *
 * This software is NOT free software.
 * You may NOT distribute it or modify it without explicit permission.
 *
 * This software has been created in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY. zProtect is NOT liable for ANY damages caused by unlawful distribution
 * or usage of any zProtect source code or products.
 *
 * You should have received a copy of the zProtect PU (Prohibited Usage) License along with this program.
 */

package dev.zprotect.obfuscator.api

import dev.zprotect.obfuscator.api.interfaces.IBytecode
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.Type
import java.util.jar.JarFile

object ClassPath : HashMap<String, ClassEntry>() {
    override fun get(key: String): ClassEntry? = super.get(key) ?: add(get(Class.forName(key.replace("/", "."))))
    fun get(classNode: ClassNode): ClassEntry = super.get(classNode.name) ?: add(ClassNodeEntry(classNode))
    fun get(clazz: Class<*>): ClassEntry = super.get(Type.getInternalName(clazz)) ?: add(ReflectionClassEntry(clazz))
    private fun add(classEntry: ClassEntry): ClassEntry = classEntry.apply { put(classEntry.getName(), classEntry) }

    fun load(paths: List<String>) = paths.forEach { load(it) }
    fun load(path: String) {
        val jar = JarFile(path)
        val entries = jar.entries()

        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()

            if (entry.name.endsWith(".class")) {
                val bytes: ByteArray = IBytecode.readBytes(jar.getInputStream(entry))
                val classNode = ClassNode()
                ClassReader(bytes).accept(classNode, ClassReader.SKIP_CODE or ClassReader.SKIP_DEBUG)
                put(classNode.name, ClassNodeEntry(classNode))
            }
        }
    }
}

abstract class ClassEntry {
    abstract fun getName(): String
    abstract fun getSuper(): String?
    abstract fun getAccess(): Int
    abstract fun getInterfaces(): MutableList<String>
    abstract fun getFields(): MutableList<FieldEntry>
    abstract fun getMethods(): MutableList<MethodEntry>
}

data class MethodEntry(val owner: ClassEntry, val name: String, val desc: String)
data class FieldEntry(val owner: ClassEntry, val name: String, val desc: String)

class ClassNodeEntry(private val classNode: ClassNode) : ClassEntry() {
    override fun getName(): String = classNode.name
    override fun getSuper(): String? = classNode.superName
    override fun getAccess(): Int = classNode.access
    override fun getInterfaces(): MutableList<String> = classNode.interfaces

    override fun getFields(): MutableList<FieldEntry> = exploreFields
    private val exploreFields: MutableList<FieldEntry> by lazy {
        val list = mutableListOf<FieldEntry>()
        for (fieldNode in classNode.fields) {
            list.add(FieldEntry(this, fieldNode.name, fieldNode.desc))
        }
        list
    }

    override fun getMethods(): MutableList<MethodEntry> = exploreMethods
    private val exploreMethods: MutableList<MethodEntry> by lazy {
        val list = mutableListOf<MethodEntry>()
        for (methodNode in classNode.methods) {
            list.add(MethodEntry(this, methodNode.name, methodNode.desc))
        }
        list
    }
}

class ReflectionClassEntry(private val clazz: Class<*>) : ClassEntry() {
    override fun getName(): String = Type.getInternalName(clazz)
    override fun getSuper(): String? = if (clazz.superclass == null) null else Type.getInternalName(clazz.superclass)
        ?: if (getName() != "java/lang/Object") "java/lang/Object" else null

    override fun getAccess(): Int = clazz.modifiers

    override fun getInterfaces(): MutableList<String> = exploreInterfaces
    private val exploreInterfaces: MutableList<String> by lazy {
        val list = mutableListOf<String>()
        for (interfaces in clazz.interfaces) {
            list.add(Type.getInternalName(interfaces))
        }
        list
    }

    override fun getFields(): MutableList<FieldEntry> = exploreFields
    private val exploreFields: MutableList<FieldEntry> by lazy {
        val list = mutableListOf<FieldEntry>()
        for (field in clazz.declaredFields) {
            list.add(FieldEntry(this, field.name, Type.getDescriptor(field.type)))
        }
        list
    }

    override fun getMethods(): MutableList<MethodEntry> = exploreMethods
    private val exploreMethods: MutableList<MethodEntry> by lazy {
        val list = mutableListOf<MethodEntry>()
        for (method in clazz.declaredMethods) {
            list.add(MethodEntry(this, method.name, Type.getMethodDescriptor(method)))
        }
        list
    }
}