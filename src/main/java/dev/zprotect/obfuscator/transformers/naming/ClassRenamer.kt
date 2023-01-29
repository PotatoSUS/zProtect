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

package dev.zprotect.obfuscator.transformers.naming

import dev.zprotect.obfuscator.api.Transformer
import dev.zprotect.obfuscator.api.encryption.Dictionary
import dev.zprotect.obfuscator.api.settings.StringConfig

object ClassRenamer : Transformer("ClassRenamer", "Renames class names.") {

    private val path = StringConfig("ClassRenamerPath")

    override fun obfuscate() {
        val remap: MutableMap<String?, String?> = HashMap()

        val prefix = if (path.value == null) "" else path.value

        classes.stream()
            .filter { classNode -> !isExcluded(classNode.name) }
            .forEach { classNode ->
                val name = prefix + Dictionary.getNewName()
                remap[classNode.name] = name
                if (classNode.name.replace("/", ".") == manifest.mainAttributes.getValue("Main-Class"))
                    manifest.mainAttributes.putValue("Main-Class", name.replace("/", "."))
            }

        applyRemap(remap)

        Dictionary.reset()
    }
}