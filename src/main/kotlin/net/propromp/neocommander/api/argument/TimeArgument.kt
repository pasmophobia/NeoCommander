package net.propromp.neocommander.api.argument

import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.exception.ArgumentParseException

class TimeArgument(name: String) : CustomArgument<Long>(name) {
    val regex = Regex("""([0-9])+([d,h,m,s,t])""")
    override fun suggest(input: String, context: NeoCommandContext, builder: SuggestionsBuilder) {
        if (input.isNotEmpty()) {
            when (input.last()) {
                'd', 'h', 'm', 's', 't' -> {}
                else -> {
                    if (input.last().digitToIntOrNull() != null) {
                        builder.add(builder.createOffset(context.input.length).apply {
                            suggest("d") { "days" }
                            suggest("h") { "hours" }
                            suggest("m") { "minutes" }
                            suggest("s") { "seconds" }
                            suggest("t") { "ticks" }
                        })
                    }
                }
            }
        }
    }

    override fun parse(context: NeoCommandContext, t: String): Long {
        var match = regex.find(t) ?: throw ArgumentParseException("Illegal input")
        var ticks = 0L

        while (true) {
            val digit = match.groups[1]!!.value.toLong()
            ticks += digit * when (match.groups[2]!!.value) {
                "d" -> 24 * 60 * 60 * 20
                "h" -> 60 * 60 * 20
                "m" -> 60 * 20
                "s" -> 20
                "t" -> 1
                else -> throw ArgumentParseException("Unexpected error")
            }
            match = match.next() ?: return ticks
        }
    }
}