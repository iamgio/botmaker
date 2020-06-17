package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.telejam.text
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.withClass
import io.github.ageofwar.telejam.messages.Message
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane

data class IfMessageStartsWith(var text: String) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return event.text?.startsWith(text) ?: false
    }

    override fun toNode(botControl: BotControlPane) = FlowPane(
            Label(getString("event.filter.IfMessageStartsWith.ifstarts")).withClass("event-action"),
            TextField(text).also {
                it.textProperty().addListener { _, _, new ->
                    text = new
                }
                it.focusedProperty().addListener { _, _, focused ->
                    if(!focused) botControl.autosave()
                }
            }
    )
}
