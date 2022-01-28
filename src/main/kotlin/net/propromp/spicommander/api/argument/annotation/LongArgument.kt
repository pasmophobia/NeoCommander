package net.propromp.spicommander.api.argument.annotation

import net.propromp.spicommander.api.annotation.Argument
import net.propromp.spicommander.api.argument.LongArgument

@Argument(LongArgument::class)
annotation class LongArgument(val min: Long = Long.MIN_VALUE, val max: Long = Long.MAX_VALUE)
