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

package dev.zprotect.obfuscator.transformers.flow

import dev.zprotect.obfuscator.api.Transformer
import dev.zprotect.obfuscator.api.encryption.Dictionary
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.FieldNode

import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.JumpInsnNode

object Flow : Transformer("Flow", "Adds fake jumps, and such to code.") {

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                var hasField = false
                val fieldName = Dictionary.generateString(8)
                classNode.methods.forEach { methodNode ->
                    methodNode.instructions.filter { it.opcode == GOTO }.forEach {
                        hasField = true
                        with(methodNode) {
                            instructions.insertBefore(it, FieldInsnNode(GETSTATIC, classNode.name, fieldName, "Z"))
                            instructions.insert(it, InsnNode(ATHROW))
                            instructions.insert(it, InsnNode(ACONST_NULL))
                            instructions.set(it, JumpInsnNode(IFEQ, (it as JumpInsnNode).label))
                        }
                    }
                }

                checkCondition(hasField) {
                    classNode.fields.add(FieldNode(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, fieldName, "Z", null, null))
                }
            }
    }

    fun checkCondition(condition: Boolean, runnable: Runnable) {
        if (!condition) {
            return
        }
        runnable.run()
    }
}