package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.ui.SVG_BELL_OFF
import eu.iamgio.botmaker.ui.SVG_BELL_RING
import eu.iamgio.botmaker.ui.SVG_REPLY
import eu.iamgio.botmaker.ui.SVG_REPLY_EMPTY
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.botcontrol.event.*
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.methods.SendMessage
import javafx.scene.layout.Pane

data class Actions<T>(val actions: MutableList<Action<T>> = mutableListOf()) : Action<T> {
    override fun run(bot: Bot, event: T, logger: ConsoleLogger) = actions.forEach { it.run(bot, event, logger) }

    override fun toNode(botControl: BotControlPane) = Pane()
}

abstract class RandomAction<T>(private val actions: Actions<T>) : Action<T> {

    override fun run(bot: Bot, event: T, logger: ConsoleLogger) = actions.actions.random().run(bot, event, logger)

    override fun toNode(botControl: BotControlPane) = buildEventBlock(
            getSpecs(), botControl, actions, "action.RandomAction.text"
    )

    abstract fun getSpecs(): EventSpecs<T>
}

class MessageRandomAction(actions: Actions<Message> = Actions(mutableListOf())) : RandomAction<Message>(actions) {
    override fun getSpecs() = MessageEventSpecs()
}

data class Reply(var text: String, var sendAsReply: Boolean = true, var notification: Boolean = true) : Action<Message> {
    override fun run(bot: Bot, event: Message, logger: ConsoleLogger) {
        val sendMessage = SendMessage()
                .text(text)
                .disableNotification(!notification)
        if (sendAsReply) {
            sendMessage.replyToMessage(event)
        } else {
            sendMessage.chat(event.chat)
        }
        logger.logMessageSent(bot.execute(sendMessage))
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("action.Reply.text"),
            field(text) {text = it},
            icon(SVG_REPLY_EMPTY, SVG_REPLY, "action.Reply.sendAsReply", sendAsReply) {sendAsReply = it},
            icon(SVG_BELL_OFF, SVG_BELL_RING, "action.Reply.notification", notification) {notification = it}
    )
}