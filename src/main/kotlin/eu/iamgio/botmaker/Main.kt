package eu.iamgio.botmaker

import eu.iamgio.botmaker.bundle.ResourceBundle
import eu.iamgio.botmaker.lib.CLIConsoleLogger
import javafx.application.Application

fun main(vararg args: String) {
    when (args.size) {
        0 -> Application.launch(BotMaker::class.java, *args)
        1 -> {
            val settings = loadSettingsOrDefault()
            ResourceBundle.load(settings.locale)
            newTelejamBot(loadBotConfiguration(args[0]), CLIConsoleLogger()).run()
        }
        else -> System.err.println("Invalid arguments")
    }
}