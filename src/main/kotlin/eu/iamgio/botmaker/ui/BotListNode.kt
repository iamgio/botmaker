package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.popup.DeletePopup
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton

/**
 * @author Giorgio Garofalo
 */
class BotListNode : BrowsableVBox(true) {

    init {
        styleClass += "bot-list"
    }

    inner class ListedBotNode(val bot: BotConfiguration) : Label(bot.name), Actionable {

        init {
            styleClass += "bot-name"
            prefWidthProperty().bind(this@BotListNode.prefWidthProperty())

            setOnMouseClicked {
                if(it.button == MouseButton.PRIMARY) {
                    if(it.clickCount == 2) {
                        open()
                    }
                } else if(it.button == MouseButton.SECONDARY) {
                    BotContextMenu(this).show(it.screenX, it.screenY)
                }
            }
        }

        fun open() {
            if(root.rightControl.currentBotControl?.bot != bot) BotControlPane(bot).show()
        }

        fun delete() {
            val bounds = boundsInParent
            val popup = DeletePopup(bot)
            popup.translateX = 10.0
            popup.translateY = bounds.maxY + 85.0
            popup.show()
        }

        override fun onAction(keyCode: KeyCode) {
            if(keyCode == KeyCode.ENTER) {
                open()
            } else if(keyCode == KeyCode.DELETE) {
                delete()
            }
        }
    }
}