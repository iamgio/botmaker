package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.bundle.getString
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem

/**
 * @author Giorgio Garofalo
 */
class BotContextMenu(private val listedBotNode: BotListNode.ListedBotNode) : ContextMenu() {

    fun show(x: Double, y: Double) = super.show(listedBotNode.scene.window, x, y)

    init {
        val openMenuItem = menuItem("botcontext.open") {
            listedBotNode.open()
        }
        val deleteMenuItem = menuItem("botcontext.delete") {
            listedBotNode.delete()
        }
        items.addAll(openMenuItem, deleteMenuItem)
    }

    private fun menuItem(langKey: String, action: () -> Unit) = MenuItem(getString(langKey))
            .apply { setOnAction { action() } }
}