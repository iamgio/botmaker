package eu.iamgio.botmaker.lib.telejam

import io.github.ageofwar.telejam.messages.*
import io.github.ageofwar.telejam.text.Text

val Message.text: Text? get() = when (this) {
    is TextMessage -> text
    is PhotoMessage -> caption.orElse(null)
    is VideoMessage -> caption.orElse(null)
    is VoiceMessage -> caption.orElse(null)
    is AudioMessage -> caption.orElse(null)
    is AnimationMessage -> caption.orElse(null)
    is DocumentMessage -> caption.orElse(null)
    is Forward<*> -> this.forwardedMessage.text
    else -> null
}

val Message.description: String? get() = when(this) {
    is TextMessage -> text.toMarkdownString()
    is PhotoMessage -> caption.map { it.toMarkdownString() }.orElse(null)
    is VideoMessage -> caption.map { it.toMarkdownString() }.orElse(null)
    is VoiceMessage -> caption.map { it.toMarkdownString() }.orElse(null)
    is AudioMessage -> caption.map { it.toMarkdownString() }.orElse(null)
    is AnimationMessage -> caption.map { it.toMarkdownString() }.orElse(null)
    is DocumentMessage -> caption.map { it.toMarkdownString() }.orElse(null)
    is Forward<*> -> this.forwardedMessage.description
    is LeftChatMemberMessage -> leftChatMember.name
    is MigrateFromChatIdMessage -> oldChatId.toString()
    is MigrateToChatIdMessage -> newChatId.toString()
    is NewChatMembersMessage -> newChatMembers.joinToString { it.name }
    is NewChatTitleMessage -> newChatTitle
    else -> null
}