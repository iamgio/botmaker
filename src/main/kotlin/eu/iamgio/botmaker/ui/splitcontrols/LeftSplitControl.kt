package eu.iamgio.botmaker.ui.splitcontrols

import eu.iamgio.botmaker.ui.BotListNode
import javafx.scene.control.Label
import javafx.scene.control.SplitPane

/**
 * @author Giorgio Garofalo
 */
class LeftSplitControl : SplitControl() {

    init {
        SplitPane.setResizableWithParent(this, false)
        styleClass += "left-control"

        navBar.children += Label("+").apply {
            styleClass += "add-bot-button"
            setOnMouseClicked {
                println("Add bot")
                // TODO add bot
            }
        }

        val botList = BotListNode().apply { prefWidthProperty().bind(this@LeftSplitControl.widthProperty()) }
        botList.children.addAll(botList.ListedBotNode("my_bot_1"), botList.ListedBotNode("my_bot_2")) // Test
        children += botList
    }
}