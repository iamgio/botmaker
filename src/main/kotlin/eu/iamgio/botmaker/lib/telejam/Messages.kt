package eu.iamgio.botmaker.lib.telejam

import io.github.ageofwar.telejam.messages.*

val Message.text get() = when (this) {
    is TextMessage -> text
    is PhotoMessage -> caption.orElse(null)
    is VideoMessage -> caption.orElse(null)
    is VoiceMessage -> caption.orElse(null)
    is AudioMessage -> caption.orElse(null)
    is AnimationMessage -> caption.orElse(null)
    is DocumentMessage -> caption.orElse(null)
    else -> null
}