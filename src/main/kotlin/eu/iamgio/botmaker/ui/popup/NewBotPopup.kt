package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.bundle.getString
import javafx.scene.control.TextField

/**
 * @author Giorgio Garofalo
 */
class NewBotPopup : ScenePopup("popup.newbot.title") {

    init {
        children += TextField().apply {
            promptText = getString("popup.newbot.name")
            nodeToFocus = this
            setOnAction { onConfirm() }
        }
        addConfirmButton("popup.newbot.create")
    }

    override fun onConfirm() {
        hide()
        // TODO add bot
    }
}