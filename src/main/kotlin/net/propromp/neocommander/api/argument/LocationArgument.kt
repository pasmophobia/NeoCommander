package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.argument.annotation.LocationArgument
import net.propromp.neocommander.api.nms.NMSUtil
import org.bukkit.Location

class LocationArgument(name: String, val type: LocationType = LocationType.ENTITY) : NeoArgument<Any, Location>(name) {
    constructor(name: String, annotation: LocationArgument) : this(name, annotation.type)

    enum class LocationType {
        BLOCK, ENTITY
    }

    override fun asBrigadier() = when (type) {
        LocationType.BLOCK -> NMSUtil.argumentPosition().invokeStaticMethod("a")
        LocationType.ENTITY -> NMSUtil.argumentVec3().invokeStaticMethod("a")
    } as ArgumentType<out Any>

    override fun parse(context: NeoCommandContext, t: Any): Location {
        val vec3D = NMSUtil.vec3d(NMSUtil.iVectorPosition(t).invokeMethod("a",context.context.source)!!)
        val x = vec3D.invokeMethod("getX") as Double
        val y = vec3D.invokeMethod("getY") as Double
        val z = vec3D.invokeMethod("getZ") as Double
        return Location(context.source.world, x, y, z)
    }
}