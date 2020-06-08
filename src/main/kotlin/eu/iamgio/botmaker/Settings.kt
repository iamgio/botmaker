package eu.iamgio.botmaker

import eu.iamgio.botmaker.lib.gson.gson
import java.io.File
import java.util.*

data class Settings(val locale: Locale = Locale.ENGLISH) {

    fun save(path: String) {
        File(path).also { it.parentFile.mkdirs() }.writer().use {
            gson.toJson(this, it)
        }
    }

    companion object {
        private fun load(path: String): Settings {
            return File(path).reader().use {
                gson.fromJson(it, Settings::class.java)
            }
        }

        fun loadOrDefault(path: String, default: Settings): Settings {
            return if (File(path).exists()) load(path) else default.also { it.save(path) }
        }
    }
}