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
import org.objectweb.asm.Opcodes.ACC_FINAL
import org.objectweb.asm.tree.FieldNode

object FinalRemover : Transformer("FinalRemover", "Removes final.") {

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                for (i in 0 until classNode.fields.size) {
                    val fieldNode: FieldNode = classNode.fields.get(i)
                    if (fieldNode.access or ACC_FINAL !== 0) fieldNode.access = fieldNode.access and ACC_FINAL.inv()
                    classNode.fields.set(i, fieldNode)
                }
            }
    }
}