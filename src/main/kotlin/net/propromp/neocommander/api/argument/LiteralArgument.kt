package net.propromp.neocommander.api.argument

import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.argument.annotation.LiteralArgument
import net.propromp.neocommander.api.exception.ArgumentParseException

class LiteralArgument(name: String, vararg val literals: String) : CustomArgument<String>(name) {
    constructor(name: String, annotation: LiteralArgument) : this(name, *annotation.literals)

    override fun suggest(input: String, context: NeoCommandContext, builder: SuggestionsBuilder) {
        literals.filter { emptyOrStartsWith(input, it, true) }.forEach(builder::suggest)
    }

    override fun parse(context: NeoCommandContext, t: String): String {
        if (literals.contains(t)) {
            return t
        } else {
            throw ArgumentParseException("Illegal input $t")
        }
    }

}