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
import org.objectweb.asm.tree.FieldNode
import java.util.*

// TODO: Choose if we want to use the regular Dictionary or use another format.

object FieldRenamer : Transformer("FieldRenamer", "Renames field names.") {

    override fun obfuscate() {
        val remap: MutableMap<String?, String?> = mutableMapOf()
        val fieldMap: MutableMap<FieldNode, ClassEntry> = mutableMapOf()

        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                classNode.fields
                    .forEach { fieldNode -> fieldMap[fieldNode] = ClassPath.get(classNode) }
            }

        for ((fieldNode, owner) in fieldMap.entries) {
            val name = Dictionary.getNewName()

            val stack = Stack<ClassEntry>()
            stack.add(owner)

            while (!stack.empty()) {
                val classEntry = stack.pop()
                remap[classEntry.getName() + "." + fieldNode.name] = name

                stack.addAll(getExtensions(classEntry))
                stack.addAll(getImplementations(classEntry))
            }
        }

        applyRemap(remap)

        Dictionary.reset()
    }
}