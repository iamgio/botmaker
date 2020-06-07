package eu.iamgio.botmaker.ui

import javafx.scene.control.Label
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
class BotListNode : VBox() {

    init {
        styleClass += "bot-list"
    }
}

/**
 * @author Giorgio Garofalo
 */
class BotNameNode(name: String) : Label(name) {

    init {
        styleClass += "bot-name"
    }
}