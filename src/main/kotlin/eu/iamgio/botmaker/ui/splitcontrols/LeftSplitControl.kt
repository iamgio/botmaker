package eu.iamgio.botmaker.ui.splitcontrols

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
    }
}