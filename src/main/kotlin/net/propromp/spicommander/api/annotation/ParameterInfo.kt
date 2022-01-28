package net.propromp.spicommander.api.annotation

import net.propromp.spicommander.api.argument.NeoArgument

class ParameterInfo(val name: String, val type: ParameterType, val argument: NeoArgument<in Any,in Any>? = null)