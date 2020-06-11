package eu.iamgio.botmaker.ui.splitcontrols

import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.ui.BotListNode
import eu.iamgio.botmaker.ui.popup.NewBotPopup
import javafx.scene.control.Label
import javafx.scene.control.SplitPane

/**
 * @author Giorgio Garofalo
 */
class LeftSplitControl(private val bots: MutableList<BotConfiguration>) : SplitControl() {

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
        botList.children.addAll(bots.map { botList.ListedBotNode(it) })

        children += botList
    }

    fun addBot(bot: BotConfiguration) {
        bots += bot
        botList.children += botList.ListedBotNode(bot)
    }

    fun removeBot(name: String) {
        val botNode = botList.children.firstOrNull { it is BotListNode.ListedBotNode && it.bot.name == name } as BotListNode.ListedBotNode
        val bot = botNode.bot
        bots -= bot
        botList.children -= botNode
    }
}