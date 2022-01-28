package net.propromp.spicommander.api.argument.annotation

import net.propromp.spicommander.api.annotation.Argument
import net.propromp.spicommander.api.argument.DoubleArgument

@Argument(DoubleArgument::class)
annotation class DoubleArgument(val min: Double = Double.MIN_VALUE, val max: Double = Double.MAX_VALUE)
