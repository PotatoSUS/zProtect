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

package dev.zprotect.obfuscator

import dev.zprotect.obfuscator.Obfuscator.info
import java.io.File
import kotlin.system.exitProcess
import java.util.Collections
import java.util.UUID

fun main(args: Array<String>) {
    if (args.size != 1) {
        Obfuscator.info("Missing argument: Config file")
        exitProcess(-1)
    }

    Obfuscator.info("Visit https://zprotect.dev for more information.\n")

    Obfuscator.run(File(args[0]))
}
