package eu.iamgio.botmaker

import eu.iamgio.botmaker.bundle.ResourceBundle
import eu.iamgio.botmaker.lib.CLIConsoleLogger
import javafx.application.Application
import java.time.format.DateTimeFormatter

fun main(vararg args: String) {
    when (args.size) {
        0 -> Application.launch(BotMaker::class.java, *args)
        1 -> {
            val settings = loadSettingsOrDefault()
            ResourceBundle.load(settings.locale)
            val consoleDateTimeFormatter = try {
                DateTimeFormatter.ofPattern(settings.consoleDateTimeFormatter)
            } catch (e: IllegalArgumentException) {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            }
            newTelejamBot(loadBotConfiguration(args[0]), CLIConsoleLogger(consoleDateTimeFormatter)).run()
        }
        else -> System.err.println("Invalid arguments")
    }
}