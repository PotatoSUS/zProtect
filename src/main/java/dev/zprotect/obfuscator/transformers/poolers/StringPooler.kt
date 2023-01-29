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

package dev.zprotect.obfuscator.transformers.poolers

import dev.zprotect.obfuscator.api.Transformer
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

object StringPooler : Transformer("StringPooler", "Moves strings into an array.") {

    private const val stringPoolerArray = "¤¶§†!|~ÇüéäçêïîìæÆ¢£¥Pƒáíúñ¿¬½¼¡«»¦ßµ±°•·²€„…‡‰Š‹Œ˜™š›œŸ¨©®¯³´¸¹¾Ð×ØÞãðõ÷øüþp"

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                val ldcInsnNodeMap: MutableMap<LdcInsnNode, MethodNode> = mutableMapOf()
                classNode.methods.forEach { methodNode ->
                    methodNode.instructions.forEach { abstractInsnNode ->
                        if (abstractInsnNode is LdcInsnNode && abstractInsnNode.cst is String) ldcInsnNodeMap[abstractInsnNode] = methodNode
                    }
                }

                if (ldcInsnNodeMap.isNotEmpty()) {
                    classNode.fields.add(
                        FieldNode(
                            (if (classNode.access and Opcodes.ACC_INTERFACE != 0) Opcodes.ACC_PUBLIC else Opcodes.ACC_PRIVATE) or (if (classNode.version > Opcodes.V1_8) 0 else Opcodes.ACC_FINAL) or Opcodes.ACC_STATIC,
                            stringPoolerArray,
                            "[Ljava/lang/String;",
                            null,
                            null
                        )
                    )

                    var clinit = getMethod(classNode, "<clinit>")
                    if (clinit == null) {
                        clinit = MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, arrayOf<String>())
                        classNode.methods.add(clinit)
                    }
                    if (clinit.instructions == null) clinit.instructions = InsnList()

                    val arrayInstructions = InsnList().apply {
                        add(getLdcInt(ldcInsnNodeMap.size)) // set array length
                        add(TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/String")) // create array

                        for ((index, ldc) in ldcInsnNodeMap.keys.withIndex()) {
                            add(InsnNode(Opcodes.DUP))
                            add(getLdcInt(index))
                            add(LdcInsnNode(ldc.cst as String))
                            add(InsnNode(Opcodes.AASTORE))

                            ldcInsnNodeMap[ldc]!!.instructions.insert(ldc, InsnList().apply {
                                add(
                                    FieldInsnNode(
                                        Opcodes.GETSTATIC,
                                        classNode.name,
                                        stringPoolerArray,
                                        "[Ljava/lang/String;"
                                    )
                                )
                                add(getLdcInt(index))
                                add(InsnNode(Opcodes.AALOAD))
                            })
                            ldcInsnNodeMap[ldc]!!.instructions.remove(ldc)
                        }

                        add(
                            FieldInsnNode(
                                Opcodes.PUTSTATIC,
                                classNode.name,
                                stringPoolerArray,
                                "[Ljava/lang/String;"
                            )
                        )
                    }

                    if (clinit.instructions == null || clinit.instructions.first == null) {
                        clinit.instructions.add(arrayInstructions)
                        clinit.instructions.add(InsnNode(Opcodes.RETURN))
                    } else {
                        clinit.instructions.insertBefore(clinit.instructions.first, arrayInstructions)
                    }
                }
            }
    }
}