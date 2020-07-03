package eu.iamgio.botmaker.lib.gson

import com.google.gson.GsonBuilder
import eu.iamgio.botmaker.lib.*
import java.util.*

internal val gson = GsonBuilder()
        .registerTypeAdapter(Locale::class.java, LocaleTypeAdapter)
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Filter::class.java, "type")
                .registerSubtype(Filters::class.java, "filters")
                .registerSubtype(IfMessageStartsWith::class.java, "start")
                .registerSubtype(IfMessageEndsWith::class.java, "end")
                .registerSubtype(IfMessageContains::class.java, "contains")
                .registerSubtype(IfMessageMatchesRegex::class.java, "regex")
                .registerSubtype(IfMessageContainsRegex::class.java, "contains_regex")
                .registerSubtype(IfMessageIsCommand::class.java, "command"))
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Action::class.java, "type")
                .registerSubtype(Actions::class.java, "actions")
                .registerSubtype(Reply::class.java, "reply"))
        .create()