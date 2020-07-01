package eu.iamgio.botmaker.ui.botcontrol

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.*
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

/**
 * @author Giorgio Garofalo
 */
class TokenBox(botControl: BotControlPane) : HBox() {

    init {
        alignment = Pos.CENTER_LEFT
        styleClass += "token-box"
        children += Label(getString("token")).withClass("token")

        val tokenField = TextField(botControl.bot.botToken)

        // Visibility on/off button
        children += createSvg().apply {
            tokenField.visibleProperty().addListener { _, _, visible ->
                content = if(visible) SVG_VISIBILITY_OFF else SVG_VISIBILITY_ON
            }
            tokenField.isVisible = false
        }.wrap().apply {
            setOnMouseClicked {
                tokenField.isVisible = !tokenField.isVisible
                tokenField.isEditable = tokenField.isVisible
                Platform.runLater {
                    if(tokenField.isVisible) {
                        tokenField.requestFocus()
                        tokenField.requestFocus()
                    } else {
                        botControl.requestFocus()
                    }
                }
            }
        }

        tokenField.prefWidthProperty().bind(root.prefWidthProperty().divide(4))
        tokenField.focusedProperty().addListener { _, _, focused ->
            if(!focused) {
                botControl.bot.botToken = tokenField.text
                botControl.autosave()
            }
        }

        children += tokenField
    }
}