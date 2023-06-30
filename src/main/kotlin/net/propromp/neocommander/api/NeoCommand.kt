package net.propromp.neocommander.api

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.CommandNode
import net.minecraft.commands.CommandDispatcher
import net.minecraft.commands.CommandListenerWrapper
import net.propromp.neocommander.api.argument.NeoArgument
import org.bukkit.command.Command
import org.bukkit.craftbukkit.v1_20_R1.command.VanillaCommandWrapper

/**
 * SpiCommand
 *
 * @property name name of this command.
 * @property aliases aliases of this command.
 * @property description description of this command.
 * @property function function that will be invoked when this command is executed.
 * @property requires check if the source can execute this command.
 * @property arguments arguments of this command.
 * @property children child commands of this command.
 */
class NeoCommand(
    val name: String,
    val aliases: List<String>,
    val description: String,
    val function: (NeoCommandContext) -> Int,
    val requires: (NeoCommandSource) -> Boolean,
    val arguments: Map<String,NeoArgument<Any, Any>>,
    val children: List<NeoCommand>,
    val parallelCommands: List<NeoCommand>
) {

    /**
     * get Brigadier's LiteralArgumentBuilder
     *
     * @return LiteralArgumentBuilder
     */
    fun getLiteralArgumentBuilder(): LiteralArgumentBuilder<Any> {
        val literal = LiteralArgumentBuilder.literal<Any>(name)

        //arguments
        getRequiredArgumentBuilder()?.let {
            literal.then(it)
        } ?: run {
            literal.executes(getBrigadierCommand())
        }

        //parallel commands
        parallelCommands.forEach { parallelCommand ->
            parallelCommand.getRequiredArgumentBuilder()?.let {
                literal.then(it)
            } ?: run {
                literal.executes(parallelCommand.getBrigadierCommand())
            }
        }

        //requires
        literal.requires { requires.invoke(NeoCommandSource(it)) }

        //children
        children.forEach {
            literal.then(it.getLiteralArgumentBuilder())
        }

        return literal
    }

    /**
     * Get Brigadier's RequiredArgumentBuilder
     *
     * @return RequiredArgumentBuilder
     */
    fun getRequiredArgumentBuilder(): RequiredArgumentBuilder<Any, out Any>? {
        var argumentBuilder: RequiredArgumentBuilder<Any, out Any>? = null
        arguments.values.reversed().forEach { argument ->
            val shallowerArgumentBuilder: RequiredArgumentBuilder<Any, out Any> =
                RequiredArgumentBuilder.argument(argument.name, argument.asBrigadier())
            argument.suggestionProvider?.let {
                shallowerArgumentBuilder.suggests(it.asBrigadier(this))
            }
            argumentBuilder = argumentBuilder?.let {
                shallowerArgumentBuilder.then(it)
            } ?: run {
                shallowerArgumentBuilder.executes(getBrigadierCommand())
            }
        }

        return argumentBuilder
    }

    fun getBrigadierCommand(): com.mojang.brigadier.Command<Any> {
        return com.mojang.brigadier.Command {
            try {
                function.invoke(NeoCommandContext(this, it))
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    /**
     * Get CraftBukkit's VanillaCommandWrapper
     *
     * @param manager command manager
     * @param commandNode command node
     * @return VanillaCommandWrapper
     */
    fun getVanillaCommandWrapper(
        minecraftDispatcher: Any,
        commandNode: CommandNode<Any>
    ): Command {
        val wrapper = VanillaCommandWrapper(
            minecraftDispatcher as CommandDispatcher,
            commandNode as CommandNode<CommandListenerWrapper>
        ).apply {
            label = name
            description = description
            aliases = aliases
            permission = null
        }
        return wrapper
    }

    /**
     * Get literal argument builder of aliases.
     *
     * @param commandNode command node
     * @return list of LiteralArgumentBuilder
     */
    fun getAliasLiteralArgumentBuilders(commandNode: CommandNode<Any>) =
        aliases.map { LiteralArgumentBuilder.literal<Any>(it).redirect(commandNode) }

}