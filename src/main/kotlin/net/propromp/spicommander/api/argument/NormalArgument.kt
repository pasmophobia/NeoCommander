package net.propromp.spicommander.api.argument

import net.propromp.spicommander.api.NeoCommandContext
import net.propromp.spicommander.api.NeoSuggestionProvider

abstract class NormalArgument<T>(
    name: String,
    suggestionProvider: NeoSuggestionProvider? = null
):NeoArgument<T,T>(name, suggestionProvider) {
    override fun parse(context: NeoCommandContext, t: T): T {
        return t
    }
}