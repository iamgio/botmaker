package eu.iamgio.botmaker.lib.gson

import com.google.gson.GsonBuilder
import eu.iamgio.botmaker.lib.*
import java.util.*

internal val gson = GsonBuilder()
        .registerTypeAdapter(Locale::class.java, LocaleTypeAdapter)
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Filter::class.java, "type")
                .registerSubtype(Filters::class.java, "filters")
                .registerSubtype(IfMessageStartsWith::class.java, "start"))
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Action::class.java, "type")
                .registerSubtype(Actions::class.java, "actions")
                .registerSubtype(Reply::class.java, "reply"))
        .create()