package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.LiteralArgument

@Argument(LiteralArgument::class)
annotation class LiteralArgument(vararg val literals: String)
