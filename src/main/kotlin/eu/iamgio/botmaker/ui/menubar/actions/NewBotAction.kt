package eu.iamgio.botmaker.ui.menubar.actions

import eu.iamgio.botmaker.ui.center
import eu.iamgio.botmaker.ui.popup.NewBotPopup

/**
 * @author Giorgio Garofalo
 */
class NewBotAction : MenuItemAction {

    override fun onAction() {
        println("Create bot")
        val popup = NewBotPopup()
        popup.center()
        popup.show()
    }
}