package eu.iamgio.botmaker.ui

import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
class BotListNode : BrowsableVBox() {

    init {
        styleClass += "bot-list"
    }

    inner class BotNameNode(name: String) : Label(name), Actionable {

        init {
            styleClass += "bot-name"
            prefWidthProperty().bind(this@BotListNode.prefWidthProperty())
        }

        override fun onAction() {
            println("Bot $text")
            // TODO
        }
    }
}