package eu.iamgio.botmaker.ui.popup

import animatefx.animation.ZoomIn
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.withClass
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
open class ScenePopup(titleKey: String) : VBox() {

    protected var nodeToFocus: Node? = null

    init {
        styleClass += "scene-popup"
        isFocusTraversable = true

        children += Label(getString(titleKey)).withClass("popup-title")

        var isRootClicked = false

        root.addEventFilter(MouseEvent.MOUSE_CLICKED) {
            isRootClicked = true
            Platform.runLater {
                if(isRootClicked) hide()
                isRootClicked = false
            }
        }
        addEventFilter(MouseEvent.MOUSE_CLICKED) {
            if(isRootClicked) isRootClicked = false
        }
    }

    fun show() {
        ZoomIn(this).setSpeed(2.5).play()
        root.children += this
        repeat(2) { nodeToFocus?.requestFocus() }
    }

    fun hide() {
        root.children -= this
    }

    open fun onConfirm() {}

    fun addConfirmButton(textKey: String) {
        children += VBox(
                Label(getString(textKey)).withClass("confirm").apply { setOnMouseClicked { onConfirm() } }
        ).apply { alignment = Pos.CENTER }
    }
}