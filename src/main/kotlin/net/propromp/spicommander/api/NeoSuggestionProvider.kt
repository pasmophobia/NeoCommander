package net.propromp.spicommander.api

import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture

class NeoSuggestionProvider(val suggestionFunction: (NeoCommandContext, SuggestionsBuilder) -> CompletableFuture<Suggestions>) {
    fun asBrigadier(command: NeoCommand): SuggestionProvider<Any> = SuggestionProvider { context, builder ->
        return@SuggestionProvider suggestionFunction.invoke(NeoCommandContext(command, context), builder)
    }
}