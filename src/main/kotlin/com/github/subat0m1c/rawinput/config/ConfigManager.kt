package com.github.subat0m1c.rawinput.config

import com.github.subat0m1c.rawinput.RawInputMain.Companion.mc
import com.github.subat0m1c.rawinput.RawInputMain.Companion.scope
import com.google.gson.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

object ConfigManager {
    val settings = ArrayList<Config<*>>()

    val gson = GsonBuilder().setPrettyPrinting().create()
    private val parser = JsonParser()

    private var loaded: Boolean = false
    private val loading = CompletableDeferred(Unit)

    private val configFile = File(mc.mcDataDir, "config/rawinput.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            println("Error creative config file\n${e.message}")
        }
    }

    init {
        scope.launch {
            loading.complete(load())
        }
    }

    fun awaitLoad() {
        if (!loaded) {
            runBlocking {
                loading.await()
            }
        }
    }

    fun load() = try {
        with (configFile.bufferedReader().use { it.readText() }) {
            if (isEmpty()) return

            val jsonArray = parser.parse(this).asJsonArray ?: return
            for (setting in jsonArray) {
                val moduleObj = setting?.asJsonObject ?: continue
                val module = settings.find { it.name == moduleObj.get("name").asString} ?: continue
                module.set(moduleObj.get("value"))
            }
        }
        loaded = true
    } catch (e: Exception) {
        println("Error loading config.\n${e.message}")
    }

    fun save() = scope.launch {
        try {
            val jsonArray = JsonArray().apply {
                for (setting in settings) {
                    add(JsonObject().apply {
                        add("name", JsonPrimitive(setting.name))
                        add("value", setting.get())
                    })
                }
            }
            configFile.bufferedWriter().use { it.write(gson.toJson(jsonArray)) }
        } catch (e: Exception) {
            println("Error saving config.\n${e.message}")
        }
    }
}