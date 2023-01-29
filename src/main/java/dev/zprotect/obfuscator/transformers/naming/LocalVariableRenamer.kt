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

import dev.zprotect.obfuscator.api.Transformer
import dev.zprotect.obfuscator.api.encryption.Dictionary
import org.objectweb.asm.tree.LocalVariableNode

object LocalVariableRenamer : Transformer("LocalVariableRenamer", "Obfuscates local variables.") {

    var hashMap = HashMap<String, Boolean>()

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                classNode.methods.stream()
                    .forEach { methodNode ->
                        //if (methodNode.localVariables == null) return@forEach
                        for (i in 0 until methodNode.localVariables.size) {
                            val localVariableNode: LocalVariableNode = methodNode.localVariables.get(i)
                            methodNode.localVariables.set(
                                i,
                                LocalVariableNode(
                                    Dictionary.getNewName(),
                                    localVariableNode.desc,
                                    null,
                                    localVariableNode.start,
                                    localVariableNode.end,
                                    i
                                )
                            )
                            hashMap[localVariableNode.desc] = java.lang.Boolean.TRUE
                        }
                    }
            }
    }
}