package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.propromp.neocommander.api.argument.annotation.IntegerArgument

class IntegerArgument(name: String, val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) :
    NormalArgument<Int>(name) {
    constructor(name: String, annotation: IntegerArgument) : this(name, annotation.min, annotation.max)

    override fun asBrigadier(): ArgumentType<Int> = IntegerArgumentType.integer(min, max)
}