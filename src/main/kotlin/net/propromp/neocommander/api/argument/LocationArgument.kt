package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.minecraft.commands.CommandListenerWrapper
import net.minecraft.commands.arguments.coordinates.ArgumentPosition
import net.minecraft.commands.arguments.coordinates.ArgumentVec3
import net.minecraft.commands.arguments.coordinates.IVectorPosition
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.argument.annotation.LocationArgument
import org.bukkit.Location

class LocationArgument(name: String, val type: LocationType = LocationType.ENTITY) : NeoArgument<Any, Location>(name) {
    constructor(name: String, annotation: LocationArgument) : this(name, annotation.type)

    enum class LocationType {
        BLOCK, ENTITY
    }

    override fun asBrigadier() = when (type) {
        LocationType.BLOCK -> ArgumentPosition.a()
        LocationType.ENTITY -> ArgumentVec3.a()
    } as ArgumentType<out Any>

    override fun parse(context: NeoCommandContext, t: Any): Location {

        val vec3D = (t as IVectorPosition).a(context.context.source as CommandListenerWrapper)

        val x = vec3D.a()
        val y = vec3D.b()
        val z = vec3D.c()
        return Location(context.source.world, x, y, z)
    }
}