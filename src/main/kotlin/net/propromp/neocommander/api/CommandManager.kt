package net.propromp.neocommander.api

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.CommandNode
import net.minecraft.commands.CommandListenerWrapper
import net.propromp.neocommander.api.annotation.AnnotationManager
import net.propromp.neocommander.api.util.removeCommand
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.craftbukkit.v1_20_R1.CraftServer
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * command manager
 *
 * @property plugin plugin
 */
class CommandManager(val plugin: Plugin) {
    val annotationManager = AnnotationManager(this)
    private val minecraftDispatcher = (Bukkit.getServer() as CraftServer).handle.b().vanillaCommandDispatcher
    private val brigadierDispatcher = minecraftDispatcher.a() as CommandDispatcher<CommandListenerWrapper>
    private val commands = mutableListOf<NeoCommand>()


    /**
     * register a command.
     * @throws IllegalArgumentException
     *
     * @param neoCommand command
     */
    fun register(neoCommand: NeoCommand) {
        // register into brigadier
        val literalArgumentBuilder = neoCommand.getLiteralArgumentBuilder()
        val commandNode =
            brigadierDispatcher.register(literalArgumentBuilder as LiteralArgumentBuilder<CommandListenerWrapper>)

        // register aliases into brigadier
        val aliasArgumentBuilders = neoCommand.getAliasLiteralArgumentBuilders(commandNode as CommandNode<Any>)
        aliasArgumentBuilders.forEach { brigadierDispatcher.register(it as LiteralArgumentBuilder<CommandListenerWrapper>) }

        // register into bukkit
        val commandMap = (Bukkit.getServer() as CraftServer).commandMap
        commandMap.register(plugin.name, neoCommand.getVanillaCommandWrapper(minecraftDispatcher, commandNode))

        // put into map
        commands += neoCommand
    }

    /**
     * unregister a command
     *
     * @param neoCommand command
     */
    fun unregister(neoCommand: NeoCommand) {
        if (!commands.contains(neoCommand)) {
            throw IllegalArgumentException("The command is not registered!")
        }

        // remove from map
        commands.remove(neoCommand)

        // unregister from bukkit
        val commandMap = (Bukkit.getServer() as CraftServer).commandMap
        val knownCommands = (Bukkit.getServer() as CraftServer).commandMap.knownCommands as MutableMap<String, Command>
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
        ArrayList(commands).forEach { unregister(it) }
        sendCommandUpdate()
    }

    /**
     * send command update packet to player.
     *
     * @param player player
     */
    fun sendCommandUpdate(player: Player) {
        minecraftDispatcher.a((player as CraftPlayer).handle)
    }

    /**
     * send command update packet to all online players.
     *
     */
    fun sendCommandUpdate() {
        Bukkit.getOnlinePlayers().forEach { sendCommandUpdate(it) }
    }
}