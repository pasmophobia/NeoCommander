package net.propromp.spicommander.api.argument.annotation

import net.propromp.spicommander.api.annotation.Argument
import net.propromp.spicommander.api.argument.StringArgument

@Argument(StringArgument::class)
annotation class StringArgument(val type: StringArgument.StringType = StringArgument.StringType.STRING)
