package net.propromp.spicommander.api.argument

import com.mojang.brigadier.arguments.BoolArgumentType

class BooleanArgument(name: String):NormalArgument<Boolean>(name) {
    override fun asBrigadier() = BoolArgumentType.bool()
}