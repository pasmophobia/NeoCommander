package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.DoubleArgument

@Argument(DoubleArgument::class)
annotation class DoubleArgument(val min: Double = Double.MIN_VALUE, val max: Double = Double.MAX_VALUE)
