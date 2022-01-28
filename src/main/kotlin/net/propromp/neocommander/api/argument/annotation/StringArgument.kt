package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.StringArgument

@Argument(StringArgument::class)
annotation class StringArgument(val type: StringArgument.StringType = StringArgument.StringType.STRING)
