package net.propromp.neocommander.api

import com.mojang.brigadier.Message
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.md_5.bungee.chat.ComponentSerializer
import net.minecraft.network.chat.IChatBaseComponent
import net.propromp.neocommander.api.exception.ArgumentParseException

class NeoCommandContext(val command: NeoCommand, val context: CommandContext<Any>) {
    companion object {
        val instances = mutableMapOf<CommandContext<Any>, NeoCommandContext>()
    }

    init {
        instances[context] = this
    }

    val source = NeoCommandSource(context.source)
    val input = context.input
    fun <T> getArgumentRaw(name: String, clazz: Class<T>) = context.getArgument(name, clazz)
    fun <T : Any> getArgument(name: String, clazz: Class<T>): T {
        try {
            val argument = command.arguments[name]!!
            return argument.parse(this, getArgumentRaw(name, Any::class.java)) as T
        } catch (e: ArgumentParseException) {
            val message = IChatBaseComponent.a(ComponentSerializer.toString(e.textComponent)) as Message
            throw CommandSyntaxException(
                SimpleCommandExceptionType(message),
                message,
                context.input,
                context.nodes.last().range.start
            )
        }
    }
}