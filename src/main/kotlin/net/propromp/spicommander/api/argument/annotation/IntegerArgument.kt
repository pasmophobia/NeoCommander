package net.propromp.spicommander.api.argument.annotation

import net.propromp.spicommander.api.annotation.Argument
import net.propromp.spicommander.api.argument.IntegerArgument

@Argument(IntegerArgument::class)
annotation class IntegerArgument(val min:Int = Int.MIN_VALUE,val max:Int = Int.MAX_VALUE)
