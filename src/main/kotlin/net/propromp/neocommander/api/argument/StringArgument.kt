package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.StringArgumentType
import net.propromp.neocommander.api.argument.annotation.StringArgument

class StringArgument(name: String, val type: StringType = StringType.STRING) : NormalArgument<String>(name) {
    constructor(name: String,annotation: StringArgument): this(name,annotation.type)

    override fun asBrigadier() = when (type) {
        StringType.WORD -> StringArgumentType.word()
        StringType.STRING -> StringArgumentType.string()
        StringType.GREEDY -> StringArgumentType.greedyString()
    }

    enum class StringType {
        WORD, STRING, GREEDY
    }
}