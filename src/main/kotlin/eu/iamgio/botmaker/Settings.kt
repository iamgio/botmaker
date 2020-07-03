package eu.iamgio.botmaker

import java.util.*

data class Settings(
        var locale: Locale = Locale.ENGLISH,
        var autoSave: Boolean = true,
        var consoleDateTimeFormatter: String = "yyyy-MM-dd HH:mm:ss"
)
