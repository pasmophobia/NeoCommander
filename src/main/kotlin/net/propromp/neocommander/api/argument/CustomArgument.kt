package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.NeoSuggestionProvider

abstract class CustomArgument<T>(name: String) : NeoArgument<String, T>(name) {
    override val suggestionProvider: NeoSuggestionProvider
        get() {
            return NeoSuggestionProvider { context, builder ->
                val input = if (context.input.split(" ").last() == "") {
                    ""
                } else {
                    context.context.nodes.last().range.get(context.input)
                }
                suggest(input, context, builder)
                return@NeoSuggestionProvider builder.buildFuture()
            }
        }

    abstract fun suggest(input: String, context: NeoCommandContext, builder: SuggestionsBuilder)
    fun emptyOrStartsWith(input: String, string: String, ignoreCase: Boolean) =
        input.isEmpty() || string.startsWith(input, ignoreCase)

    override fun asBrigadier(): ArgumentType<String> = StringArgumentType.string()
}