package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.BOT_CONFIGURATIONS_PATH
import eu.iamgio.botmaker.lib.gson.gson
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.*
import io.github.ageofwar.telejam.methods.SendMessage
import java.io.File

data class BotConfiguration(val name: String, val botToken: String, val messageEvents: List<MessageEvent>) {

    val path: String
        get() = BOT_CONFIGURATIONS_PATH + File.separator + name + ".json"

    fun save(path: String = this.path) {
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
data class IfMessageStartsWith(val text: String) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return when (event) {
            is TextMessage -> event.text.startsWith(text)
            is PhotoMessage -> event.caption.map { it.startsWith(text) }.orElse(false)
            is VideoMessage -> event.caption.map { it.startsWith(text) }.orElse(false)
            is VoiceMessage -> event.caption.map { it.startsWith(text) }.orElse(false)
            is AudioMessage -> event.caption.map { it.startsWith(text) }.orElse(false)
            is AnimationMessage -> event.caption.map { it.startsWith(text) }.orElse(false)
            is DocumentMessage -> event.caption.map { it.startsWith(text) }.orElse(false)
            else -> false
        }
    }
}

interface Action<T> {
    fun run(bot: Bot, event: T)
}
data class Actions<T>(val actions: List<Action<T>>) : Action<T> {
    override fun run(bot: Bot, event: T) = actions.forEach { it.run(bot, event) }
}
data class Reply(val text: String, val sendAsReply: Boolean = true, val notification: Boolean = true) : Action<Message> {
    override fun run(bot: Bot, event: Message) {
        val sendMessage = SendMessage()
                .text(text)
                .disableNotification(!notification)
        if (sendAsReply) {
            sendMessage.replyToMessage(event)
        } else {
            sendMessage.chat(event.chat)
        }
        bot.execute(sendMessage)
    }
}