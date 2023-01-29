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

package dev.zprotect.obfuscator.transformers.naming

import dev.zprotect.obfuscator.api.ClassEntry
import dev.zprotect.obfuscator.api.ClassPath
import dev.zprotect.obfuscator.api.Transformer
import dev.zprotect.obfuscator.api.encryption.Dictionary
import dev.zprotect.obfuscator.hasAccess
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.MethodNode
import java.util.*

object MethodRenamer : Transformer("MethodRenamer", "Renames method names.") {
    private val badNames = arrayListOf("main")

    override fun obfuscate() {
        val remap: MutableMap<String?, String?> = mutableMapOf()
        val methodMap: MutableMap<MethodNode, ClassEntry> = mutableMapOf()

        // bruh classNode.access and ACC_ANNOTATION == 0 && classNode.access and ACC_ENUM == 0 && classNode.access and ACC_ABSTRACT == 0

        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) && classNode.hasAccess(ACC_ANNOTATION or ACC_ENUM or ACC_ABSTRACT) }
            .forEach { classNode ->
                classNode.methods.stream()
                    .filter { methodNode -> !badNames.contains(methodNode.name) && !methodNode.name.startsWith("<") && methodNode.hasAccess(ACC_NATIVE) }
                    .forEach { methodNode -> methodMap[methodNode] = ClassPath.get(classNode) }
            }

        methods@
        for ((methodNode, owner) in methodMap.entries) {
            val stack = Stack<ClassEntry>()
            stack.add(owner)

            while (stack.isNotEmpty()) {
                val classEntry = stack.pop()

                if (classEntry != owner && classEntry.getMethods()
                        .findLast { method -> method.name == methodNode.name && method.desc == methodNode.desc } != null
                )
                    continue@methods

                val parent = if (classEntry.getSuper() == null) null else ClassPath[classEntry.getSuper()]
                if (parent != null) stack.push(parent)

                classEntry.getInterfaces().forEach { inter: String ->
                    val interfNode = ClassPath[inter]
                    if (interfNode != null) stack.push(interfNode)
                }
            }

            val name = Dictionary.getNewName()
            stack.add(owner)

            while (stack.isNotEmpty()) {
                val classEntry = stack.pop()
                remap[classEntry.getName() + "." + methodNode.name + methodNode.desc] = name

                stack.addAll(getExtensions(classEntry))
                stack.addAll(getImplementations(classEntry))
            }
        }

        applyRemap(remap)

        Dictionary.reset()
    }
}