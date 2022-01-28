package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.LongArgument

@Argument(LongArgument::class)
annotation class LongArgument(val min: Long = Long.MIN_VALUE, val max: Long = Long.MAX_VALUE)
