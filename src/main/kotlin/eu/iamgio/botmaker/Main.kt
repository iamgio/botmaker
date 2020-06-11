package eu.iamgio.botmaker

import eu.iamgio.botmaker.lib.BotConfiguration
import javafx.application.Application

fun main(vararg args: String) {
    when (args.size) {
        0 -> Application.launch(BotMaker::class.java, *args)
        1 -> {
            val botName = args[0]
            TelejamBot(BotConfiguration.fromJson("$BOT_CONFIGURATIONS_PATH/$botName.json")).run()
        }
        else -> System.err.println("Invalid arguments")
    }
}