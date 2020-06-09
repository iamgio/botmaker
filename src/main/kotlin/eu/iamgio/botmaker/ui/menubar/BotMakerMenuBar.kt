package eu.iamgio.botmaker.ui.menubar

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.ui.menubar.actions.MenuItemAction
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCodeCombination

/**
 * @author Giorgio Garofalo
 */
class BotMakerMenuBar : MenuBar() {

    fun menu(menuNameKey: String, items: BotMakerMenu.() -> Unit) = BotMakerMenu(menuNameKey).items()

    inner class BotMakerMenu(private val menuNameKey: String) : Menu(getString("menubar.$menuNameKey")) {

        init {
            this@BotMakerMenuBar.menus += this
        }

        fun item(menuItemNameKey: String, combination: KeyCodeCombination? = null, action: MenuItemAction) =
                BotMakerMenuItem(menuItemNameKey, combination, action)

        inner class BotMakerMenuItem(menuItemNameKey: String, combination: KeyCodeCombination?, action: MenuItemAction) : MenuItem(getString("menubar.$menuNameKey.$menuItemNameKey")) {

            init {
                if(combination != null) accelerator = combination
                setOnAction { action.onAction() }
                this@BotMakerMenu.items += this
            }
        }
    }
}

fun menubar(menus: BotMakerMenuBar.() -> Unit) = BotMakerMenuBar().apply { menus() }