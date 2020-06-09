package eu.iamgio.botmaker.ui.botcontrol

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.ui.*
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox

/**
 * @author Giorgio Garofalo
 */
class TokenBox(bot: BotConfiguration) : HBox() {

    init {
        alignment = Pos.CENTER_LEFT
        styleClass += "token-box"
        children += Label(getString("token")).withClass("token")

        val tokenLabel = Label(bot.botToken).withClass("token-label")

        children += createSvg().apply {
            tokenLabel.visibleProperty().addListener { _, _, visible ->
                content = if(visible) SVG_VISIBILITY_OFF else SVG_VISIBILITY_ON
            }
            tokenLabel.isVisible = false
        }.wrap().apply {
            setOnMouseClicked {
                tokenLabel.isVisible = !tokenLabel.isVisible
            }
        }

        children += createSvg(SVG_EDIT).wrap() // TODO token edit

        children += tokenLabel
    }
}