package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.ui.*
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.botcontrol.event.buildEventLine
import eu.iamgio.botmaker.ui.botcontrol.event.field
import eu.iamgio.botmaker.ui.botcontrol.event.icon
import eu.iamgio.botmaker.ui.botcontrol.event.text
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.methods.SendMessage
import javafx.scene.control.Label
import javafx.scene.layout.VBox

data class Actions<T>(val actions: MutableList<Action<T>>) : Action<T> {
    override fun run(bot: Bot, event: T) = actions.forEach { it.run(bot, event) }

    override fun toNode(botControl: BotControlPane) = VBox(
            *actions.map { it.toNode(botControl) }.toTypedArray(),
            Label("+ ${getString("new.action")}").withClass("new").apply {
                setOnMouseClicked {
                    println("New action")
                    // TODO new action
                }
            }
    )
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