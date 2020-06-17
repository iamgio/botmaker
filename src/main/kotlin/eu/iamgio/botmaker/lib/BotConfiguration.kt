package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.Message
import javafx.scene.Node

data class BotConfiguration(var botToken: String, val messageEvents: MutableList<Event<Message>> = mutableListOf())

data class Event<T>(val filter: Filter<T>, val action: Action<T>)

interface Filter<T> {
    fun filter(event: T): Boolean
    fun toNode(botControl: BotControlPane): Node
}

interface Action<T> {
    fun run(bot: Bot, event: T)
    fun toNode(botControl: BotControlPane): Node
}
