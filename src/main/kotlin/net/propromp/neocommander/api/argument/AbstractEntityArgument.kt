package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.nms.NMSUtil

abstract class AbstractEntityArgument<T>(name: String, val type: Pair<Boolean, Boolean>) :
    NeoArgument<Any, T>(name) {
    companion object {
        val SINGLE_PLAYER = true to true
        val SINGLE_ENTITY = true to false
        val SEVERAL_PLAYERS = false to true
        val SEVERAL_ENTITIES = false to false
    }

    final override fun asBrigadier() = NMSUtil.argumentEntity(type.first, type.second).instance as ArgumentType<out Any>

    @Suppress
    final override fun parse(context: NeoCommandContext, t: Any) = when (type) {
        SINGLE_PLAYER -> {
            NMSUtil.entityPlayer(NMSUtil.entitySelector(t).invokeMethod("c", context.context.source)!!)
                .invokeMethod("getBukkitEntity")
        }
        SINGLE_ENTITY -> {
            NMSUtil.entity(NMSUtil.entitySelector(t).invokeMethod("a", context.context.source)!!)
                .invokeMethod("getBukkitEntity")
        }
        SEVERAL_PLAYERS -> {
            val players = (NMSUtil.entitySelector(t).invokeMethod("d", context.context.source) as List<Any>)
            players.map { NMSUtil.entityPlayer(it).invokeMethod("getBukkitEntity") }
        }
        SEVERAL_ENTITIES -> {
            val players = (NMSUtil.entitySelector(t).invokeMethod("getEntities", context.context.source) as List<Any>)
            players.map { NMSUtil.entity(it).invokeMethod("getBukkitEntity") }
        }
        else -> null!!
    } as T
}