package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.IntegerArgument

@Argument(IntegerArgument::class)
annotation class IntegerArgument(val min:Int = Int.MIN_VALUE,val max:Int = Int.MAX_VALUE)
