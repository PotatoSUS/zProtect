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

object KotlinMetadataRemover : Transformer("KotlinMetadataRemover", "Removes Kotlin Metadata.") {

    override fun obfuscate() {

        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->

                if (classNode.visibleAnnotations != null) {
                    classNode.visibleAnnotations =
                        classNode.visibleAnnotations.filter { it.desc != "Lkotlin/Metadata;" }
                }

                val annotations = arrayOf(
                    "Lorg/jetbrains/annotations/NotNull;",
                    "Lorg/jetbrains/annotations/JvmName;",
                    "Lorg/jetbrains/annotations/Nullable;"
                )

                classNode.methods.forEach { method ->
                    if (method.invisibleAnnotations != null) {
                        method.invisibleAnnotations =
                            method.invisibleAnnotations.filterNot { annotations.contains(it.desc) }
                    }

                    if (method.visibleAnnotations != null) {
                        method.visibleAnnotations =
                            method.visibleAnnotations.filterNot { annotations.contains(it.desc) }
                    }
                }

                classNode.fields.forEach { field ->
                    if (field.invisibleAnnotations != null) {
                        field.invisibleAnnotations =
                            field.invisibleAnnotations.filterNot { annotations.contains(it.desc) }
                    }

                    if (field.visibleAnnotations != null) {
                        field.visibleAnnotations = field.visibleAnnotations.filterNot { annotations.contains(it.desc) }
                    }
                }
            }
    }
}