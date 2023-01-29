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

//private const val ANNOTATION_REF = "java/lang/annotation/Annotation"

object HideClassMembers : Transformer("HideClassMembers", "Mark classes as synthetic to hide them from bad decompilers.") {

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->

                /*if (!classNode.editable) return
                classNode.modified = true

                if (!classNode.dependencies.contains(ANNOTATION_REF)) {
                    classNode.access = classNode.access or Opcodes.ACC_SYNTHETIC
                }*/


                classNode.methods.forEach { methodNode ->
                    methodNode.access = methodNode.access or Opcodes.ACC_SYNTHETIC or Opcodes.ACC_BRIDGE
                }

                classNode.fields.forEach { fieldNode ->
                    fieldNode.access = fieldNode.access or Opcodes.ACC_SYNTHETIC
                }
            }
    }
}