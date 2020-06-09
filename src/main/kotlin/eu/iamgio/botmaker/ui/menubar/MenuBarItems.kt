package eu.iamgio.botmaker.ui.menubar

import eu.iamgio.botmaker.ui.menubar.actions.NewBotAction
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination

fun createMenuBar() = menubar {

    menu("file") {

        item("new", KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), NewBotAction())
    }
}