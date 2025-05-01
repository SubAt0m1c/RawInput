package com.github.subat0m1c.rawinput.config

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import kotlin.reflect.KProperty

class Config <T : Any> (val name: String, private val Json: JsonManager<T>) {
    var value = Json.default

    init {
        ConfigManager.settings.add(this)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun set(value: JsonElement) {
        this.value = Json.read(value)
    }

    fun get() = Json.write(value)

    companion object {
        fun booleanSetting(default: Boolean) = JsonManager(
            default,
            { Json: JsonElement -> Json.asBoolean },
            { value: Boolean -> JsonPrimitive(value) }
        )

        fun intSetting(default: Int) = JsonManager(
            default,
            { Json: JsonElement -> Json.asInt },
            { value: Int -> JsonPrimitive(value) }
        )
    }
}

data class JsonManager <T : Any> (val default: T, val read: (JsonElement) -> T, val write: (T) -> JsonElement)