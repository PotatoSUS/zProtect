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

package dev.zprotect.obfuscator.transformers.decompiler

import dev.zprotect.obfuscator.api.Transformer
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

/* Use noverify for now, I would suggest not using this transformer and use
 BadAnnotationCrasher instead even though it's weaker and more stupid.
 */

object DecompilerCrasher : Transformer("DecompilerCrasher", "Fucks the decompiler.") {

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                // Just don't code in minecraft...
                //if (classNode.name.contains("Mixin")) return@forEach
                classNode.methods.filterNot { it.name == "<clinit>" }.forEach { method ->
                    if (method.instructions.first != null) {
                        val label = LabelNode(Label())
                        val insnList = InsnList().apply {
                            //add(emitIntPush(0))
                            add(JumpInsnNode(Opcodes.IFNE, label))
                            add(InvokeDynamicInsnNode("i am sad your java got yeeted", "()V", Handle(6, "a", "a", "(IIIIIIIIIIIIIIIIIII)I")))
                            add(InsnNode(Opcodes.ACONST_NULL))
                            add(TypeInsnNode(Opcodes.CHECKCAST, "give up and buy zprotect"))
                            // TODO: Push to stack lol
                            add(InsnNode(Opcodes.LDC)) // prob do trick idk
                            // if you push to stack the bytecode would probably be kinda valid ngl lmao
                            //add(InsnNode(Opcodes.POP)) Causes crash when you run the program...
                            add(label)
                        }

                        method.instructions.insertBefore(method.instructions.first, insnList)
                    }
                }
            }
    }
}