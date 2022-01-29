package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.FloatArgumentType
import net.propromp.neocommander.api.argument.annotation.FloatArgument

class FloatArgument(
    name: String,

    val min: Float = Float.MIN_VALUE,
    val max: Float = Float.MAX_VALUE
) :
    NormalArgument<Float>(name) {
    constructor(name: String, annotation: FloatArgument) : this(
        name,
        annotation.min,
        annotation.max
    )

    override fun asBrigadier() = FloatArgumentType.floatArg(min, max)
}