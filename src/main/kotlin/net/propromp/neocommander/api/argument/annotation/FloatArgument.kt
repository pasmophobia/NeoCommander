package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.FloatArgument

@Argument(FloatArgument::class)
annotation class FloatArgument(val min: Float = Float.MIN_VALUE, val max: Float = Float.MAX_VALUE)
