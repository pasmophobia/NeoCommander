package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.minecraft.commands.CommandListenerWrapper
import net.minecraft.commands.arguments.ArgumentEntity
import net.minecraft.commands.arguments.selector.EntitySelector
import net.propromp.neocommander.api.NeoCommandContext

abstract class AbstractEntityArgument<T>(name: String, val type: Pair<Boolean, Boolean>) :
    NeoArgument<Any, T>(name) {
    companion object {
        val SINGLE_PLAYER = true to true
        val SINGLE_ENTITY = true to false
        val SEVERAL_PLAYERS = false to true
        val SEVERAL_ENTITIES = false to false
    }

    final override fun asBrigadier() = ArgumentEntity::class.java.getDeclaredConstructor(
        *arrayOf(
            Boolean::class.javaPrimitiveType!!,
            Boolean::class.javaPrimitiveType!!
        )
    ).apply {
        isAccessible = true
    }.newInstance(type.first, type.second) as ArgumentType<out Any>

    final override fun parse(context: NeoCommandContext, t: Any) = when (type) {
        SINGLE_PLAYER -> {
            (t as EntitySelector).c(context.context.source as CommandListenerWrapper).bukkitEntity
        }

        SINGLE_ENTITY -> {
            (t as EntitySelector).a(context.context.source as CommandListenerWrapper).bukkitEntity
        }

        SEVERAL_PLAYERS -> {
            val players = (t as EntitySelector).d(context.context.source as CommandListenerWrapper)

            players.map { it.bukkitEntity }
        }
        SEVERAL_ENTITIES -> {

            val players = (t as EntitySelector).b(context.context.source as CommandListenerWrapper)
            players.map { it.bukkitEntity }
        }
        else -> null!!
    } as T


}