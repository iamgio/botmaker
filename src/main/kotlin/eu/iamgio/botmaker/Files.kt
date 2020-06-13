package eu.iamgio.botmaker

import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.lib.gson.gson
import java.io.File

const val SETTINGS_PATH = "./settings.json"
const val BOT_CONFIGURATIONS_PATH = "./bots"

fun getBotConfigurationPath(name: String) = "$BOT_CONFIGURATIONS_PATH/$name.json"

fun BotConfiguration.save(name: String) {
    File(getBotConfigurationPath(name)).also { it.parentFile.mkdirs() }.writer().use {
        gson.toJson(this, it)
    }
}

fun loadBotConfiguration(name: String): BotConfiguration {
    return File(getBotConfigurationPath(name)).reader().use {
        gson.fromJson(it, BotConfiguration::class.java)
    }
}

fun loadBotConfigurations(): MutableMap<String, BotConfiguration> {
    return File(BOT_CONFIGURATIONS_PATH).also { it.mkdirs() }.list()!!.associateTo(mutableMapOf()) {
        val name = it.removeSuffix(".json")
        name to loadBotConfiguration(name)
    }
}

fun deleteBotConfiguration(name: String) {
    File(getBotConfigurationPath(name)).delete()
}

fun Settings.save() {
    File(SETTINGS_PATH).also { it.parentFile.mkdirs() }.writer().use {
        gson.toJson(this, it)
    }
}

fun loadSettings(): Settings {
    return File(SETTINGS_PATH).reader().use {
        gson.fromJson(it, Settings::class.java)
    }
}

fun loadSettingsOrDefault(default: Settings = Settings()): Settings {
    return if (File(SETTINGS_PATH).exists()) loadSettings() else default.also { it.save() }
}
