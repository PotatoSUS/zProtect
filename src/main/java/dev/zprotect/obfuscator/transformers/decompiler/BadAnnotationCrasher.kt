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
import joptsimple.internal.Strings
import org.objectweb.asm.tree.AnnotationNode


object BadAnnotationCrasher :
    Transformer(
        "BadAnnotationCrasher",
        "Generates invisible annotations which will cause Procyon to be very slow."
    ) {

    private val string: String = Strings.repeat('\n', 40) // I think making this any higher is a bad idea...

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                if (classNode.invisibleAnnotations == null)
                    classNode.invisibleAnnotations = arrayListOf()

                classNode.invisibleAnnotations.add(getAnnotationNode())

                classNode.fields.forEach { fieldNode ->
                    if (fieldNode.invisibleAnnotations == null) {
                        fieldNode.invisibleAnnotations = arrayListOf()

                        fieldNode.invisibleAnnotations.add(getAnnotationNode())
                    }
                }

                classNode.methods.forEach { methodNode ->
                    if (methodNode.invisibleAnnotations == null) {
                        methodNode.invisibleAnnotations = arrayListOf()

                        methodNode.invisibleAnnotations.add(getAnnotationNode())
                    }
                }
            }
    }

    private fun getAnnotationNode(): AnnotationNode? {
        return AnnotationNode(string)
    }
}


