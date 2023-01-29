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
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.MethodInsnNode

object EnumOptimiser : Transformer("EnumOptimiser", "Removes clone call and return array.") {

    override fun obfuscate() {
        classes.stream()
                .filter { classNode -> !isExcluded(classNode.name) }
                .forEach { classNode ->
                    if (classNode.access.hasAccess(ACC_ENUM)) {
                        val desc = "[L${classNode.name};"

                        val valuesMethod = classNode.methods.firstOrNull {
                            it.name == "values"
                                    &&
                                    it.desc == "()$desc"
                                    &&
                                    it.instructions.size() >= 4
                        } ?: return@forEach

                        for (insnNode in valuesMethod.instructions) {
                            if (insnNode is MethodInsnNode) {
                                if (
                                        insnNode.opcode == INVOKEVIRTUAL
                                        &&
                                        insnNode.name == "clone"
                                ) {
                                    if (insnNode.next.opcode == CHECKCAST) {
                                        valuesMethod.instructions.remove(insnNode.next)
                                    }
                                    valuesMethod.instructions.remove(insnNode)
                                }
                            }
                        }
                    }
                }
    }
    private fun Int.hasAccess(access: Int) = this and access != 0
}