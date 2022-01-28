package net.propromp.spicommander.api.builder

import net.propromp.spicommander.api.NeoCommand
import net.propromp.spicommander.api.NeoCommandContext
import net.propromp.spicommander.api.NeoCommandSource
import net.propromp.spicommander.api.argument.NeoArgument
import org.bukkit.command.CommandSender

data class CommandBuilder(
    private val name: String,
    private val aliases: List<String> = listOf(),
    private val description: String = "",
    private val function: (NeoCommandContext) -> Int = { 0 },
    private val requirements: List<(NeoCommandSource) -> Boolean> = listOf(),
    private val arguments: List<NeoArgument<out Any,out Any>> = listOf(),
    private val children: List<NeoCommand> = listOf()
) {
    fun build() = NeoCommand(
        name,
        aliases,
        description,
        function,
        { source -> requirements.filterNot {
            it.invoke(source)
        }.isEmpty() },
        arguments,
        children
    )

    fun aliases(vararg aliases: String) = copy(aliases = aliases.toList())
    fun appendAliases(vararg aliases: String) = copy(
        name,
        this.aliases.toMutableList().apply { addAll(aliases) },
        description,
        function,
        requirements,
        arguments,
        children
    )

    fun description(description: String) = copy(description = description)
    fun executesWithReturn(function: (NeoCommandContext) -> Int) = copy(function = function)
    fun executes(function: (NeoCommandContext) -> Unit) = copy(function = { function.invoke(it);0 })
    fun requirement(requires: List<(NeoCommandSource) -> Boolean>) = copy(requirements = requires)
    fun appendRequirement(require: (NeoCommandSource) -> Boolean) =
        copy(requirements = requirements.toMutableList().apply { add(require) })

    fun requiresPermission(permission: String) = appendRequirement { it.sender.hasPermission(permission) }
    fun requiresSender(clazz: Class<out CommandSender>) = appendRequirement { clazz.isInstance(it.sender) }
    fun arguments(vararg arguments: NeoArgument<out Any,out Any>) = copy(arguments = arguments.toList())
    fun appendArguments(vararg arguments: NeoArgument<out Any,out Any>) =
        copy(arguments = this.arguments.toMutableList().apply { addAll(arguments) })

    fun children(vararg children: NeoCommand) = copy(children = children.toList())
    fun appendChildren(vararg children: NeoCommand) =
        copy(children = this.children.toMutableList().apply { addAll(children) })

}