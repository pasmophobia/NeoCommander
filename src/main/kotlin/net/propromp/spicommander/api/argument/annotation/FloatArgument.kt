package net.propromp.spicommander.api.argument.annotation

import net.propromp.spicommander.api.annotation.Argument
import net.propromp.spicommander.api.argument.FloatArgument

@Argument(FloatArgument::class)
annotation class FloatArgument(val min: Float = Float.MIN_VALUE, val max: Float = Float.MAX_VALUE)
