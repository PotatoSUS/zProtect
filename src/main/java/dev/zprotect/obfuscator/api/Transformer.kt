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

import dev.zprotect.obfuscator.Obfuscator
import dev.zprotect.obfuscator.Obfuscator.info
import dev.zprotect.obfuscator.api.settings.BooleanConfig
import dev.zprotect.obfuscator.api.interfaces.INode
import dev.zprotect.obfuscator.api.interfaces.IBytecode
import org.objectweb.asm.commons.ClassRemapper
import org.objectweb.asm.commons.SimpleRemapper
import org.objectweb.asm.tree.ClassNode
import java.util.jar.Manifest
import java.util.stream.Collectors

abstract class Transformer(val name: String, val description: String) : IBytecode, INode {
    private val setting = BooleanConfig(name)

    val classMap: MutableMap<String, ClassNode>
        get() = Obfuscator.getClassNodes()

    val classes: List<ClassNode>
        get() = ArrayList(classMap.values)

    val filesMap: MutableMap<String, ByteArray>
        get() = Obfuscator.getFiles()

    val files: List<String>
        get() = ArrayList(filesMap.keys)

    val manifest: Manifest
        get() = Obfuscator.getManifest()

    fun run() {
        if (setting.value != null && setting.value!!) {
            obfuscate()
            info("Applied transformer: $name")
        }
    }

    protected abstract fun obfuscate()

    fun applyRemap(remap: Map<String?, String?>?) {
        val remapper = SimpleRemapper(remap)
        for (node in classes) {
            val copy = ClassNode()
            val adapter = ClassRemapper(copy, remapper)
            node.accept(adapter)
            classMap.remove(node.name)
            classMap[node.name] = copy
        }
    }

    fun getImplementations(target: ClassNode): List<ClassNode> {
        return classes.stream().filter { cn -> cn.interfaces.contains(target.name) }.collect(Collectors.toList())
    }

    fun getExtensions(target: ClassNode): List<ClassNode> {
        val extensions: MutableList<ClassNode> = mutableListOf()

        classes.stream()
            .filter { classNode -> classNode.superName == target.name }
            .forEach { classNode ->
                extensions.add(classNode)
                extensions.addAll(getExtensions(classNode))
            }

        return extensions
    }

    fun getImplementations(target: ClassEntry): List<ClassEntry> {
        val implementations = mutableListOf<ClassEntry>()
        for (classNode in classes.stream().filter { classNode -> classNode.interfaces.contains(target.getName()) }) {
            implementations.add(ClassPath.get(classNode))
            implementations.addAll(getImplementations(ClassPath.get(classNode)))
        }
        return implementations
    }

    fun getExtensions(target: ClassEntry): List<ClassEntry> {
        val extensions = mutableListOf<ClassEntry>()
        classes.stream()
            .filter { classNode -> classNode.superName == target.getName() }
            .forEach { classNode ->
                extensions.add(ClassPath.get(classNode))
                extensions.addAll(getExtensions(ClassPath.get(classNode)))
            }
        return extensions
    }
}