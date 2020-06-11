package eu.iamgio.botmaker.ui.splitcontrols

import eu.iamgio.botmaker.ui.bindSize
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

const val NAVBAR_HEIGHT = 60.0

/**
 * @author Giorgio Garofalo
 */
open class SplitControl : VBox() {

    protected val navBar = HBox().apply {
        styleClass += "nav-bar"
        bindSize(this@SplitControl, bindHeight = false)
        prefHeight = NAVBAR_HEIGHT
        maxHeight = NAVBAR_HEIGHT
        alignment = Pos.CENTER_LEFT
    }

    init {
        styleClass += "split-control"
        children += navBar
    }
}