package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.BOT_CONFIGURATIONS_PATH
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.text.TextAlignment
import java.io.File

/**
 * @author Giorgio Garofalo
 */
class DeletePopup(private val bot: BotConfiguration) : ScenePopup("popup.delete.title") {

    init {
        alignment = Pos.CENTER

        children += Label(getString("popup.delete.warning")).apply { textAlignment = TextAlignment.CENTER }
        addConfirmButton("popup.delete.delete")
    }

    override fun onConfirm() {
        root.leftControl.removeBot(bot)
        File(BOT_CONFIGURATIONS_PATH + File.separator + bot.name + ".json").delete()

        hide()
    }
}