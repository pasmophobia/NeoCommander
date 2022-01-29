package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.LongArgumentType
import net.propromp.neocommander.api.argument.annotation.LongArgument

class LongArgument(
    name: String,
    val min: Long = Long.MIN_VALUE,
    val max: Long = Long.MAX_VALUE
) :
    NormalArgument<Long>(name) {
    constructor(name: String, annotation: LongArgument) : this(
        name,
        annotation.min,
        annotation.max
    )

    override fun asBrigadier() = LongArgumentType.longArg(min, max)
}