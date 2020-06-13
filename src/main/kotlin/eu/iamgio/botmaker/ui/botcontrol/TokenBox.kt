package eu.iamgio.botmaker.ui.botcontrol

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox

/**
 * @author Giorgio Garofalo
 */
class TokenBox(bot: BotConfiguration, botControl: BotControlPane) : HBox() {

    init {
        alignment = Pos.CENTER_LEFT
        styleClass += "token-box"
        children += Label(getString("token")).withClass("token")

        val editableToken = EditableToken(bot, botControl)

        // Visibility on/off button
        children += createSvg().apply {
            editableToken.visibleProperty().addListener { _, _, visible ->
                content = if(visible) SVG_VISIBILITY_OFF else SVG_VISIBILITY_ON
            }
            editableToken.isVisible = false
        }.wrap().apply {
            setOnMouseClicked {
                editableToken.isVisible = !editableToken.isVisible
                if(editableToken.isEditing) editableToken.toggle()
            }
        }

        // Edit button
        children += createSvg(SVG_EDIT).wrap().apply {
            disableProperty().bind(editableToken.visibleProperty().not())
            visibleProperty().bind(disableProperty().not())
            setOnMouseClicked {
                editableToken.toggle()
            }
        }

        children += editableToken
    }
}

private class EditableToken(bot: BotConfiguration, botControl: BotControlPane) : HBox() {

    private val label = Label(bot.botToken).withClass("token-label").apply { isWrapText = true }
    private val textField = TextField()

    val editingProperty = SimpleBooleanProperty()
    val isEditing: Boolean
        get() = editingProperty.value

    init {
        alignment = Pos.CENTER_LEFT

        editingProperty.addListener { _ ->
            if(isEditing) {
                textField.text = label.text
                children.setAll(textField)
            } else {
                children.setAll(label)
            }
        }

        editingProperty.bind(label.visibleProperty().not())

        textField.visibleProperty().bind(editingProperty)
        textField.disableProperty().bind(editingProperty.not())
        label.disableProperty().bind(editingProperty)

        label.prefWidthProperty().bind(root.prefWidthProperty().divide(5))
        textField.prefWidthProperty().bind(root.prefWidthProperty().divide(4))

        textField.setOnAction {
            label.text = textField.text
            toggle()
            // bot.botToken = label.text
            botControl.autosave()
        }
        textField.setOnKeyReleased {
            if(it.code == KeyCode.ESCAPE) toggle()
        }
    }

    fun toggle() {
        label.isVisible = !label.isVisible

        if(isEditing) {
            textField.requestFocus()
            textField.requestFocus()
        }
    }
}