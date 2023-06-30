package net.propromp.neocommander.api

import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.chat.ComponentSerializer
import net.minecraft.commands.CommandListenerWrapper
import net.minecraft.network.chat.IChatBaseComponent
import org.bukkit.World

class NeoCommandSource(src: Any) {
    val source = src as CommandListenerWrapper
    val sender = source.bukkitSender
    val world: World? = source.e()?.let { (it as net.minecraft.world.level.World).world }
    fun sendMessage(message: TextComponent, logAdmin: Boolean = true) {
        source.a({ IChatBaseComponent.a(ComponentSerializer.toString(message)) }, logAdmin)
    }

    fun sendMessage(message: Component, logAdmin: Boolean = true) {
        source.a({ IChatBaseComponent.a(ComponentSerializer.toString(message)) }, logAdmin)
    }

    fun sendMessage(message: String, logAdmin: Boolean = true) = sendMessage(TextComponent(message), logAdmin)
    fun sendFailureMessage(message: TextComponent): Int {
        source.b(IChatBaseComponent.a(ComponentSerializer.toString(message)))
        return 0
    }

    fun sendFailureMessage(message: Component): Int {
        source.b(IChatBaseComponent.a(ComponentSerializer.toString(message)))
        return 0
    }

    fun sendFailureMessage(message: String) = sendFailureMessage(TextComponent(message))
}