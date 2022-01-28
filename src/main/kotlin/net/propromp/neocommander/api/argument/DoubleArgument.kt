package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.DoubleArgumentType
import net.propromp.neocommander.api.argument.annotation.DoubleArgument

class DoubleArgument(name: String, val min: Double = Double.MIN_VALUE, val max: Double = Double.MAX_VALUE) :
    NormalArgument<Double>(name) {
    constructor(name: String, annotation: DoubleArgument) : this(name, annotation.min, annotation.max)

    override fun asBrigadier() = DoubleArgumentType.doubleArg(min, max)
}