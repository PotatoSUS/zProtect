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

package dev.zprotect.obfuscator.transformers.optimization

import dev.zprotect.obfuscator.api.Transformer
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

object InsnRemover : Transformer("InsnRemover", "Removes the instructions const_. and tableswitch.") {

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                classNode.methods.forEach { methodNode ->
                    val newInsnSet = InsnList()
                    insn@ for (abstractInsnNode in methodNode.instructions.toArray()) {
                        if (abstractInsnNode is TableSwitchInsnNode) {

                            val labelNodeStart = LabelNode()
                            val clearStack = InsnNode(Opcodes.POP)

                            val jumpInsnNodeStart = JumpInsnNode(Opcodes.GOTO, abstractInsnNode.dflt)
                            val labelNodeEnd = LabelNode()
                            val jumpInsnNodeEnd = JumpInsnNode(Opcodes.GOTO, labelNodeEnd)

                            newInsnSet.add(jumpInsnNodeEnd)
                            newInsnSet.add(labelNodeStart)
                            newInsnSet.add(clearStack)
                            newInsnSet.add(jumpInsnNodeStart)
                            newInsnSet.add(labelNodeEnd)

                            newInsnSet.add(InsnNode(Opcodes.DUP))
                            newInsnSet.add(LdcInsnNode(-abstractInsnNode.min))
                            newInsnSet.add(InsnNode(Opcodes.IADD))
                            newInsnSet.add(JumpInsnNode(Opcodes.IFLT, labelNodeStart))
                            newInsnSet.add(InsnNode(Opcodes.DUP))
                            newInsnSet.add(LdcInsnNode(-abstractInsnNode.max))
                            newInsnSet.add(InsnNode(Opcodes.IADD))
                            newInsnSet.add(JumpInsnNode(Opcodes.IFGT, labelNodeStart))
                            newInsnSet.add(InsnNode(Opcodes.DUP))
                            newInsnSet.add(LdcInsnNode(-abstractInsnNode.min))
                            newInsnSet.add(InsnNode(Opcodes.IADD))
                            var labelIndex = 0
                            for (label in abstractInsnNode.labels) {
                                val nextBranch = LabelNode()
                                newInsnSet.add(InsnNode(Opcodes.DUP))
                                newInsnSet.add(JumpInsnNode(Opcodes.IFNE, nextBranch))
                                newInsnSet.add(InsnNode(Opcodes.POP))
                                newInsnSet.add(InsnNode(Opcodes.POP))
                                newInsnSet.add(JumpInsnNode(Opcodes.GOTO, label))
                                newInsnSet.add(nextBranch)
                                if (labelIndex + 1 != abstractInsnNode.labels.size) {
                                    newInsnSet.add(LdcInsnNode(-1))
                                    newInsnSet.add(InsnNode(Opcodes.IADD))
                                }
                                labelIndex++
                            }
                            newInsnSet.add(InsnNode(Opcodes.POP))
                            newInsnSet.add(JumpInsnNode(Opcodes.GOTO, labelNodeStart))
                        } else {
                            when (abstractInsnNode.opcode) {
                                Opcodes.ICONST_M1, Opcodes.ICONST_0, Opcodes.ICONST_1, Opcodes.ICONST_2, Opcodes.ICONST_3, Opcodes.ICONST_4, Opcodes.ICONST_5 -> {
                                    newInsnSet.add(LdcInsnNode(abstractInsnNode.opcode - 3))
                                    continue@insn
                                }
                                Opcodes.LCONST_0, Opcodes.LCONST_1 -> {
                                    newInsnSet.add(LdcInsnNode(abstractInsnNode.opcode - 9L))
                                    continue@insn
                                }
                                Opcodes.FCONST_0, Opcodes.FCONST_1, Opcodes.FCONST_2 -> {
                                    newInsnSet.add(LdcInsnNode(abstractInsnNode.opcode - 11f))
                                    continue@insn
                                }
                                Opcodes.DCONST_0, Opcodes.DCONST_1 -> {
                                    newInsnSet.add(LdcInsnNode(abstractInsnNode.opcode - 14.0))
                                    continue@insn
                                }
                            }
                        }
                        newInsnSet.add(abstractInsnNode)
                    }
                    methodNode.instructions = newInsnSet
                }
            }
    }
}


