package eu.iamgio.botmaker.ui.console

import eu.iamgio.botmaker.ui.withClass
import javafx.application.Platform
import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
class ConsoleLogger(private val console: ConsoleNode) {

    fun log(text: String) {
        Platform.runLater { console.children += Label(text).withClass("log") }
    }

    fun logError(text: String) {
        Platform.runLater { console.children += Label(text).withClass("log").withClass("log-error") }
    }
}