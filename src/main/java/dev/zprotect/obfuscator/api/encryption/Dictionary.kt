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

package dev.zprotect.obfuscator.api.encryption

import java.security.SecureRandom

object Dictionary {
    private val RANDOM = SecureRandom()
    private val ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private var num = 0

    fun getNewName(): String {
        return getInAlphabet(num++)
    }

    fun reset() {
        num = 0
    }

    private fun getInAlphabet(i: Int): String {
        return if (i < 0) "" else getInAlphabet(i / 52 - 1) + (toAscii(i % 52)).toChar()
    }

    private fun toAscii(i: Int): Int {
        return if (i < 26) i + 97 else i + 39
    }

    fun genRandomString(): String {
        val length = RANDOM.nextInt(15 - 5) + 5
        val stringBuilder = StringBuilder()
        for (i in 0 until length) {
            val letter = RANDOM.nextInt(ALPHABET.length)
            stringBuilder.append(ALPHABET[letter])
        }
        return stringBuilder.toString()
    }

    fun generateString(length: Int): String {
        val length = length
        val stringBuilder = StringBuilder()
        for (i in 0 until length) {
            val letter = RANDOM.nextInt(ALPHABET.length)
            stringBuilder.append(ALPHABET[letter])
        }
        return stringBuilder.toString()
    }
}