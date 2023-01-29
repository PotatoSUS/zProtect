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

import dev.zprotect.obfuscator.api.ClassPath
import dev.zprotect.obfuscator.api.Transformer
import dev.zprotect.obfuscator.api.interfaces.IBytecode
import dev.zprotect.obfuscator.api.settings.ArrayConfig
import dev.zprotect.obfuscator.api.settings.BooleanConfig
import dev.zprotect.obfuscator.api.settings.Config
import dev.zprotect.obfuscator.api.settings.StringConfig
import dev.zprotect.obfuscator.transformers.antitamper.AntiDebug
import dev.zprotect.obfuscator.transformers.decompiler.BadAnnotationCrasher
import dev.zprotect.obfuscator.transformers.decompiler.DecompilerCrasher
import dev.zprotect.obfuscator.transformers.flow.Flow
import dev.zprotect.obfuscator.transformers.naming.ClassRenamer
import dev.zprotect.obfuscator.transformers.naming.FieldRenamer
import dev.zprotect.obfuscator.transformers.naming.LocalVariableRenamer
import dev.zprotect.obfuscator.transformers.naming.MethodRenamer
import dev.zprotect.obfuscator.transformers.optimization.*
import dev.zprotect.obfuscator.transformers.poolers.NumberPooler
import dev.zprotect.obfuscator.transformers.poolers.StringPooler
import dev.zprotect.obfuscator.transformers.shrinking.*
import dev.zprotect.obfuscator.transformers.shufflers.ShuffleFields
import dev.zprotect.obfuscator.transformers.shufflers.ShuffleMethods
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.Instant
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.jar.Manifest
import kotlin.random.Random


object Obfuscator {
    private val inputJar = StringConfig("inputJar")
    private val outputJar = StringConfig("outputJar")
    private val exclusions = ArrayConfig("exclusions")
    private val libraries = ArrayConfig("libraries")

    private val watermark = BooleanConfig("watermark")

    private lateinit var ioMode: IOMode
    private val files: MutableMap<String, ByteArray> = HashMap()
    private val classNodes: MutableMap<String, ClassNode> = HashMap()
    private lateinit var manifest: Manifest

    @JvmStatic
    fun info(msg: String) {
        println("[${Instant.now()}] $msg")
    }

    fun run(config: File) {
        val startTime = System.currentTimeMillis()

        Config.read(config)

        if (inputJar.value == null || outputJar.value == null) {
            info("Error: Input file or output file is not specified!")
            return
        }

        val inputFile = File(inputJar.value!!)
        if (!inputFile.exists()) {
            info("Input file not found!")
            return
        }
        openFile(inputFile)

        ClassPath.load(getLibraries())

        arrayListOf(
            // Flow
            Flow,

            // Shrinking
            RemoveInnerClasses,
            LocalVariableRemover,
            LineNumberRemover,
            SourceDebugRemover,
            SourceFileRemover,

            // Shuffling
            ShuffleFields,
            ShuffleMethods,

            // Pooler
            NumberPooler,
            StringPooler,

            // Optimization
            KotlinMetadataRemover,
            HideClassMembers,
            RemoveSignatures,
            FinalRemover,
            InsnRemover,
            EnumOptimiser,
            NOPInsnRemover,


            // Naming
            MethodRenamer,
            FieldRenamer,
            ClassRenamer,
            LocalVariableRenamer,

            // Reversing
            DecompilerCrasher,
            AntiDebug,
            BadAnnotationCrasher,
        ).forEach(Transformer::run)

        saveFile()

        info("zProtect has finished obfuscating in " + (System.currentTimeMillis() - startTime) + " milliseconds.")
    }

    private fun openFile(file: File) {
        if (file.name.endsWith(".jar")) {
            ioMode = IOMode.JAR
            openJar(JarFile(file))
        } else if (file.name.endsWith(".class")) {
            ioMode = IOMode.CLASS
            openClass(file)
        }
    }

    private fun openJar(jar: JarFile) {
        val entries = jar.entries()

        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()

            val bytes: ByteArray = IBytecode.readBytes(jar.getInputStream(entry))

            if (!entry.name.endsWith(".class")) {
                files[entry.name] = bytes
            } else {
                val classNode = ClassNode()
                ClassReader(bytes).accept(classNode, ClassReader.EXPAND_FRAMES)
                classNodes[classNode.name] = classNode
                ClassPath.get(classNode)
            }
        }
        manifest = jar.manifest
    }

    private fun openClass(file: File) {
        val bytes: ByteArray = IBytecode.readBytes(file.inputStream())
        val classNode = ClassNode()
        ClassReader(bytes).accept(classNode, ClassReader.EXPAND_FRAMES)
        classNodes[classNode.name] = classNode
    }

    private fun saveFile() {
        when (ioMode) {
            IOMode.JAR -> saveJar()
            IOMode.CLASS -> saveClass()
        }
    }

    private fun saveJar() {
        /*
        not P
        TODO: Figure out why CRC corrupter doesn't work with JarOutputStream
        Even when we set ZipOutputStream and use the function it does not work...
        Here is the function:

        fun corruptCRC32(zipOutputStream: ZipOutputStream?) {
        try {
            val field: Field = ZipOutputStream::class.java.getDeclaredField("crc")
            field.isAccessible = true
            field.set(zipOutputStream, object : CRC32() {
                override fun getValue(): Long {
                    return -0x21523f22
                }
            })
        } catch (ignored: NoSuchFieldException) {
        } catch (ignored: IllegalAccessException) {}
        }

        FIX THIS PLEASE...
        */

        val byteArrayOutputStream = ByteArrayOutputStream()
        manifest.write(byteArrayOutputStream)
        files["META-INF/MANIFEST.MF"] = byteArrayOutputStream.toByteArray()

        var location: String = outputJar.value!!
        if (!location.endsWith(".jar")) location += ".jar"

        val jarPath = Paths.get(location)
        Files.deleteIfExists(jarPath)

        val outJar = JarOutputStream(
            Files.newOutputStream(
                jarPath,
                StandardOpenOption.CREATE,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE
            )
        )

        classNodes.values.forEach { classNode ->
            outJar.putNextEntry(JarEntry(classNode.name + ".class"))
            outJar.write(IBytecode.writeBytes(classNode))
            outJar.closeEntry()
        }

        files.entries.forEach { (key, value) ->
            outJar.putNextEntry(JarEntry(key))
            outJar.write(value)
            outJar.closeEntry()
        }

        // Our unique identifier so we can check if a program has been obfuscated with zProtect or not.
        // Set a comment in jar, can be seen once opening a jar in a zip viewer.
        if (watermark.value == true) {
            outJar.setComment("오줌 싸기, 맨 위에 검은 소프트웨어에서 쉬십시오! 지금 네그로워 구매.")

            // Dummy class.
            val dummy = ClassNode()
            dummy.visit(
                Opcodes.V1_5,
                Opcodes.ACC_PUBLIC,
                "오줌 싸기, 맨 위에 검은 소프트웨어에서 쉬십시오! 지금 네그로워 구매.",
                null,
                "java/lang/Object",
                null
            )
            dummy.visitMethod(Random.nextInt(100), "\u0001", "(\u0001/)L\u0001/;", null, null)
            try {
                outJar.putNextEntry(JarEntry(dummy.name + ".class"))
                outJar.write(IBytecode.toByteArrayDefault(dummy))
                info("Applied transformer: Watermark")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        outJar.close()
    }

    private fun saveClass() {
        var location: String = outputJar.value!!
        if (!location.endsWith(".class")) location += ".class"

        val classPath = Paths.get(location)
        Files.deleteIfExists(classPath)

        val outputStream = Files.newOutputStream(
            classPath,
            StandardOpenOption.CREATE,
            StandardOpenOption.CREATE_NEW,
            StandardOpenOption.WRITE
        )
        outputStream.write(IBytecode.writeBytes(classNodes.entries.iterator().next().value))
        outputStream.close()
    }

    fun getClassNodes(): MutableMap<String, ClassNode> {
        return classNodes
    }

    fun getFiles(): MutableMap<String, ByteArray> {
        return files
    }

    fun getManifest(): Manifest {
        return manifest
    }

    fun getExclusions(): List<String> { // TODO: Caching for faster performance.
        val exclusionsCache: MutableList<String> = mutableListOf()
        if (exclusions.value == null) return exclusionsCache
        for (i in 0 until exclusions.value!!.length()) exclusionsCache.add(exclusions.value!!.getString(i))
        return exclusionsCache
    }

    fun getLibraries(): List<String> { // TODO: Caching for faster performance.
        val librariesCache: MutableList<String> = mutableListOf()
        if (libraries.value == null) return librariesCache
        for (i in 0 until libraries.value!!.length()) librariesCache.add(libraries.value!!.getString(i))
        return librariesCache
    }

    enum class IOMode {
        CLASS, JAR;
    }
}