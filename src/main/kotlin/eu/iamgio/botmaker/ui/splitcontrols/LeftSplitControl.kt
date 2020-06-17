package eu.iamgio.botmaker.ui.splitcontrols

import eu.iamgio.botmaker.Settings
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.ui.BotListNode
import eu.iamgio.botmaker.ui.popup.NewBotPopup
import javafx.scene.control.Label
import javafx.scene.control.SplitPane

/**
 * @author Giorgio Garofalo
 */
class LeftSplitControl(private val bots: MutableMap<String, BotConfiguration>, private val settings: Settings) : SplitControl() {

    private val botList: BotListNode

    init {
        SplitPane.setResizableWithParent(this, false)
        styleClass += "left-control"

        navBar.children += Label("+").apply {
            styleClass += "add-bot-button"
            setOnMouseClicked {
                println("Add bot")
                val popup = NewBotPopup()
                popup.translateX = 5.0
                popup.translateY = NAVBAR_HEIGHT - 5.0
                popup.show()
            }
        }

        botList = BotListNode().apply { prefWidthProperty().bind(this@LeftSplitControl.widthProperty()) }
        botList.children.addAll(bots.map { (name, bot) -> botList.ListedBotNode(name, bot, settings) })

        children += botList
    }

    fun addBot(name: String, bot: BotConfiguration) {
        bots[name] = bot
        botList.children += botList.ListedBotNode(name, bot, settings)
    }

    fun removeBot(name: String) {
        bots -= name
        botList.children -= botList.children.filterIsInstance<BotListNode.ListedBotNode>()
                .firstOrNull { it.name == name }
    }
}