package net.propromp.spicommander.api

import net.propromp.spicommander.api.annotation.AnnotationManager
import net.propromp.spicommander.api.nms.NMS
import net.propromp.spicommander.api.nms.NMSUtil
import net.propromp.spicommander.api.nms.NMSUtil.toEntityPlayer
import net.propromp.spicommander.api.util.removeCommand
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import com.mojang.brigadier.CommandDispatcher as BrigadierDispatcher

/**
 * command manager
 *
 * @property plugin plugin
 */
class CommandManager(val plugin: Plugin) {
    val annotationManager = AnnotationManager(this)
    private val minecraftDispatcher: NMS = NMSUtil.minecraftDispatcher()
    private val brigadierDispatcher: BrigadierDispatcher<in Any> = minecraftDispatcher.invokeMethod("a") as BrigadierDispatcher<in Any>
    private val commands = mutableMapOf<String, NeoCommand>()

    /**
     * register a command.
     * @throws IllegalArgumentException
     *
     * @param neoCommand command
     */
    fun register(neoCommand: NeoCommand) {
        if (commands.contains(neoCommand.name)) {
            throw IllegalArgumentException("Command with the same name already exists!")
        }

        // register into brigadier
        val literalArgumentBuilder = neoCommand.getLiteralArgumentBuilder()
        val commandNode = brigadierDispatcher.register(literalArgumentBuilder)

        // register aliases into brigadier
        val aliasArgumentBuilders = neoCommand.getAliasLiteralArgumentBuilders(commandNode)
        aliasArgumentBuilders.forEach { brigadierDispatcher.register(it) }

        // register into bukkit
        val commandMap = NMSUtil.simpleCommandMap().instance as CommandMap
        commandMap.register(plugin.name, neoCommand.getVanillaCommandWrapper(minecraftDispatcher.instance, commandNode))

        // put into map
        commands[neoCommand.name] = neoCommand
    }

    /**
     * unregister a command
     *
     * @param neoCommand command
     */
    fun unregister(neoCommand: NeoCommand) {
        if (!commands.contains(neoCommand.name)) {
            throw IllegalArgumentException("The command is not registered!")
        }

        // remove from map
        commands.remove(neoCommand.name)

        // unregister from bukkit
        val commandMap = Bukkit.getCommandMap()
        val knownCommands = NMSUtil.simpleCommandMap().getField("knownCommands") as MutableMap<String,Command>
        knownCommands.remove("${plugin.name}:${neoCommand.name}")?.unregister(commandMap)
        knownCommands.remove(neoCommand.name)?.unregister(commandMap)

        // unregister aliases from brigadier
        neoCommand.aliases.forEach {
            brigadierDispatcher.root.removeCommand(it)
        }
        brigadierDispatcher.root.removeCommand("${plugin.name}:${neoCommand.name}")//registered by bukkit

        // unregister from brigadier
        brigadierDispatcher.root.removeCommand(neoCommand.name)
        brigadierDispatcher.root.removeCommand("${plugin.name}:${neoCommand.name}")
        brigadierDispatcher.root.removeCommand("minecraft:${neoCommand.name}")
    }

    /**
     * clear commands registered by this command manager
     */
    fun clearCommands() {
        commands.values.forEach { unregister(it) }
        sendCommandUpdate()
    }

    /**
     * send command update packet to player.
     *
     * @param player player
     */
    fun sendCommandUpdate(player: Player) {
        minecraftDispatcher.invokeMethod("a",player.toEntityPlayer())
    }

    /**
     * send command update packet to all online players.
     *
     */
    fun sendCommandUpdate() {
        Bukkit.getOnlinePlayers().forEach { sendCommandUpdate(it) }
    }
}