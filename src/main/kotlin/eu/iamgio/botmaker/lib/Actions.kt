package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.ui.SVG_BELL_OFF
import eu.iamgio.botmaker.ui.SVG_BELL_RING
import eu.iamgio.botmaker.ui.SVG_REPLY
import eu.iamgio.botmaker.ui.SVG_REPLY_EMPTY
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.botcontrol.event.buildEventLine
import eu.iamgio.botmaker.ui.botcontrol.event.field
import eu.iamgio.botmaker.ui.botcontrol.event.icon
import eu.iamgio.botmaker.ui.botcontrol.event.text
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.methods.SendMessage
import javafx.scene.layout.Pane

data class Actions<T>(val actions: MutableList<Action<T>> = mutableListOf()) : Action<T> {
    override fun run(bot: Bot, event: T) = actions.forEach { it.run(bot, event) }

    override fun toNode(botControl: BotControlPane) = Pane()
}

data class Reply(var text: String, var sendAsReply: Boolean = true, var notification: Boolean = true) : Action<Message> {
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

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("action.Reply.reply"),
            field(text) {text = it},
            icon(SVG_REPLY_EMPTY, SVG_REPLY, "action.Reply.sendAsReply", sendAsReply) {sendAsReply = it},
            icon(SVG_BELL_OFF, SVG_BELL_RING, "action.Reply.notification", notification) {notification = it}
    )
}