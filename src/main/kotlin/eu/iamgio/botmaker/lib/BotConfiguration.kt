package eu.iamgio.botmaker.lib

import com.google.gson.GsonBuilder
import eu.iamgio.botmaker.lib.gson.RuntimeTypeAdapterFactory
import io.github.ageofwar.telejam.messages.Message
import java.io.File

private val gson = GsonBuilder()
        .registerTypeHierarchyAdapter(Filter::class.java, RuntimeTypeAdapterFactory.of(Filter::class.java, "type")
                .registerSubtype(Filters::class.java, "filters"))
        .registerTypeHierarchyAdapter(Action::class.java, RuntimeTypeAdapterFactory.of(Action::class.java, "type")
                .registerSubtype(Actions::class.java, "actions"))
        .create()

data class BotConfiguration(val messageEvents: List<MessageEvent>) {
    fun save(path: String) {
        File(path).also { it.parentFile.mkdirs() }.writer().use {
            gson.toJson(this, it)
        }
    }

    companion object {
        fun fromJson(path: String): BotConfiguration {
            return File(path).reader().use {
                gson.fromJson(it, BotConfiguration::class.java)
            }
        }
    }
}

data class MessageEvent(val filter: Filter<Message>, val action: Action<Message>)

interface Filter<T> {
    fun filter(event: T): Boolean
}
data class Filters<T>(val filters: List<List<Filter<T>>>) : Filter<T> {
    override fun filter(event: T): Boolean {
        return filters.all { requirements -> requirements.any { it.filter(event) } }
    }
}

interface Action<T> {
    fun run(event: T)
}
data class Actions<T>(val actions: List<Action<T>>) : Action<T> {
    override fun run(event: T) = actions.forEach { it.run(event) }
}