package eu.iamgio.botmaker

import javafx.application.Application

fun main(vararg args: String) {
    when (args.size) {
        0 -> Application.launch(BotMaker::class.java, *args)
        1 -> TelejamBot(loadBotConfiguration(args[0])).run()
        else -> System.err.println("Invalid arguments")
    }
}