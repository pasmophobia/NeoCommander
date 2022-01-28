package net.propromp.neocommander.api.exception

import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.TextComponent


class ArgumentParseException(val textComponent: TextComponent):Exception() {
    constructor(component: Component):this(TextComponent(component.toString()))
    constructor(message: String): this(TextComponent(message))
}