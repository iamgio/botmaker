package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.save
import javafx.scene.control.TextField

/**
 * @author Giorgio Garofalo
 */
class NewBotPopup : ScenePopup(getString("popup.newbot.title")) {

    private val nameField = TextField()
    private val tokenField = TextField()

    init {
        children += nameField.apply {
            promptText = getString("popup.newbot.name")
            nodeToFocus = this
            setOnAction { onConfirm() }
            textProperty().addListener { _ -> removeError(this)}
        }
        children += tokenField.apply {
            promptText = getString("popup.newbot.token")
            setOnAction { onConfirm() }
        }
        addConfirmButton("popup.newbot.create")
    }

    override fun onConfirm() {
        if(nameField.text.isEmpty() || !nameField.text.matches("\\w+".toRegex())) {
            addError(nameField)
            return
        }
        hide()
        val token = tokenField.text
        val name = nameField.text

        val bot = BotConfiguration(token, mutableListOf())
        bot.save(name)
        val listedBot = root.leftControl.addBot(name, bot)
        listedBot.open()
    }
}