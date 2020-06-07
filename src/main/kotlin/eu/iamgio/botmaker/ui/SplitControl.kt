package eu.iamgio.botmaker.ui

import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

private const val NAVBAR_HEIGHT = 50.0

/**
 * @author Giorgio Garofalo
 */
class SplitControl : VBox() {

    private val navBar = HBox().apply {
        styleClass += "nav-bar"
        bindSize(this@SplitControl, bindHeight = false)
        prefHeight = NAVBAR_HEIGHT
    }

    init {
        styleClass += "split-control"
        children += navBar
    }
}