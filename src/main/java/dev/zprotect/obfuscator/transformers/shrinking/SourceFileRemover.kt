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

package dev.zprotect.obfuscator.transformers.shrinking

import dev.zprotect.obfuscator.api.Transformer

object SourceFileRemover : Transformer("SourceFileRemover", "Removes the SourceFile attribute.") {

    override fun obfuscate() {
        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                classNode.sourceFile = null
            }
    }
}