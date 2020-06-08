package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
class BotListNode : BrowsableVBox(true) {

    init {
        styleClass += "bot-list"
    }

    inner class BotNameNode(private val name: String) : Label(name), Actionable {

        init {
            styleClass += "bot-name"
            prefWidthProperty().bind(this@BotListNode.prefWidthProperty())
        }

        private fun open() {
            println("Bot $text")
            BotControlPane(name).show()
        }

        override fun onAction() = open()
    }
}