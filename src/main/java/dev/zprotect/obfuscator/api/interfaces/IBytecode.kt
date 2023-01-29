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

package dev.zprotect.obfuscator.api.interfaces

import dev.zprotect.obfuscator.Obfuscator
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import java.io.ByteArrayOutputStream
import java.io.InputStream

interface IBytecode {
    companion object : IBytecode

    fun isExcluded(name: String): Boolean {
        val path = getPath(name)
        for (exclusion in Obfuscator.getExclusions()) {
            if (path.contains(exclusion)) return true
        }
        return false
    }

    fun getPath(name: String): String {
        if (!name.contains("/")) return ""
        val reversedString = name.reversed()
        val path = reversedString.substring(reversedString.indexOf("/"))
        return path.reversed()
    }

    fun getMethod(classNode: ClassNode, method: String): MethodNode? {
        classNode.methods.forEach { methodNode ->
            if (methodNode.name == method) return methodNode
        }

        return null
    }

    fun readBytes(inputStream: InputStream): ByteArray {
        val bytes: ByteArray
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buf = ByteArray(256)
        var n: Int
        while (inputStream.read(buf).also { n = it } != -1) byteArrayOutputStream.write(buf, 0, n)
        bytes = byteArrayOutputStream.toByteArray()

        return bytes
    }

    fun writeBytes(classNode: ClassNode): ByteArray {
        val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
        writer.newUTF8("Obfuscated with zProtect, visit https://zprotect.dev for more information.")
        classNode.accept(writer)
        return writer.toByteArray()
    }

    fun toByteArrayDefault(classNode: ClassNode): ByteArray? {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
        classNode.accept(classWriter)
        return classWriter.toByteArray()
    }
}