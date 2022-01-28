package net.propromp.neocommander.api.argument

import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.NeoSuggestionProvider

abstract class NormalArgument<T>(
    name: String,
    suggestionProvider: NeoSuggestionProvider? = null
):NeoArgument<T,T>(name, suggestionProvider) {
    override fun parse(context: NeoCommandContext, t: T): T {
        return t
    }
}