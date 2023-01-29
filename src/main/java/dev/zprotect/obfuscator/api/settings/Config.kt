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

package dev.zprotect.obfuscator.api.settings

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileInputStream

open class Config<T>(private val name: String, private val type: Type, private val read: (JSONObject) -> T) {
    var value: T? = null

    companion object {
        private lateinit var READ_OBJECT: JSONObject
        private val TO_READ: ArrayList<Config<*>> = arrayListOf()
        private val IS_INITIALIZED: Boolean
            get() = ::READ_OBJECT.isInitialized

        fun read(file: File?) {
            READ_OBJECT = JSONObject(JSONTokener(FileInputStream(file)))
            TO_READ.forEach { it.read(READ_OBJECT) }
        }
    }

    init {
        if (IS_INITIALIZED) read(READ_OBJECT)
        else TO_READ.add(this)
    }

    private fun read(jsonObject: JSONObject) {
        value = if (jsonObject.has(name)) read.invoke(jsonObject) else null
    }

    enum class Type {
        ARRAY, STRING, BOOLEAN
    }
}

class ArrayConfig(name: String?) : Config<JSONArray?>(name!!, Type.ARRAY, {
    it.getJSONArray(name)
})

class StringConfig(name: String?) : Config<String?>(name!!, Type.STRING, {
    it.getString(name)
})

class BooleanConfig(name: String?) : Config<Boolean?>(name!!, Type.BOOLEAN, {
    it.getBoolean(name)
})

