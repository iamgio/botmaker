package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.deleteBotConfiguration
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.text.TextAlignment

/**
 * @author Giorgio Garofalo
 */
class DeletePopup(private val name: String, private val bot: BotConfiguration) : ScenePopup(getString("popup.delete.title", name)) {

    init {
        alignment = Pos.CENTER

        children += Label(getString("popup.delete.warning")).apply { textAlignment = TextAlignment.CENTER }
        addConfirmButton("popup.delete.delete")
    }

    override fun onConfirm() {
        if(root.rightControl.currentBotControl?.name == name) root.rightControl.children.removeAt(1)

        root.leftControl.removeBot(name)
        deleteBotConfiguration(name)

        hide()
    }
}