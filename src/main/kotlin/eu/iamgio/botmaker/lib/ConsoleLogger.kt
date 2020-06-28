package eu.iamgio.botmaker.lib

/**
 * @author Giorgio Garofalo
 */
interface ConsoleLogger {
    fun log(text: String)
    fun logError(text: String)
}

class CLIConsoleLogger : ConsoleLogger {
    override fun log(text: String) {
        println(text)
    }

    override fun logError(text: String) {
        System.err.println(text)
    }
}