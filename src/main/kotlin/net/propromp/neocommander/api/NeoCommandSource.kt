package net.propromp.neocommander.api

import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.TextComponent
import net.propromp.neocommander.api.nms.NMSUtil
import net.propromp.neocommander.api.nms.NMSUtil.toIChatBaseComponent
import org.bukkit.World
import org.bukkit.command.CommandSender

class NeoCommandSource(src: Any) {
    val source = NMSUtil.commandListenerWrapper().apply { instance = src }
    val sender = source.invokeMethod("getBukkitSender") as CommandSender
    val world: World? = source.invokeMethod("getWorld")?.let {NMSUtil.world(it).invokeMethod("getWorld") as World? }
    fun sendMessage(message: TextComponent, logAdmin: Boolean = true) {
        source.invokeMethodBySelectingTypes(
            "sendMessage",
            arrayOf(message.toIChatBaseComponent().instance, logAdmin),
            arrayOf(NMSUtil.iChatBaseComponent().clazz, Boolean::class.java)
        )
    }

    fun sendMessage(message: Component, logAdmin: Boolean = true) {
        source.invokeMethodBySelectingTypes(
            "sendMessage",
            arrayOf(message.toIChatBaseComponent().instance, logAdmin),
            arrayOf(NMSUtil.iChatBaseComponent().clazz, Boolean::class.java)
        )
    }

    fun sendMessage(message: String, logAdmin: Boolean = true) = sendMessage(TextComponent(message), logAdmin)
    fun sendFailureMessage(message: TextComponent): Int {
        source.invokeMethodBySelectingTypes(
            "sendFailureMessage",
            arrayOf(message.toIChatBaseComponent().instance),
            arrayOf(NMSUtil.iChatBaseComponent().clazz)
        )
        return 0
    }

    fun sendFailureMessage(message: Component): Int {
        source.invokeMethodBySelectingTypes(
            "sendFailureMessage",
            arrayOf(message.toIChatBaseComponent().instance),
            arrayOf(NMSUtil.iChatBaseComponent().clazz)
        )
        return 0
    }

    fun sendFailureMessage(message: String) = sendFailureMessage(TextComponent(message))
}