package net.propromp.neocommander.api

import com.mojang.brigadier.context.CommandContext

class NeoCommandContext(val command: NeoCommand, val context: CommandContext<Any>) {
    companion object {
        val instances = mutableMapOf<CommandContext<Any>,NeoCommandContext>()
    }
    init {
        instances[context] = this
    }
    val source = NeoCommandSource(context.source)
    val input = context.input
    fun <T> getArgument(name: String,clazz:Class<T>) = context.getArgument(name, clazz)
}