package net.propromp.neocommander.api.annotation

import net.propromp.neocommander.api.argument.NeoArgument

class ParameterInfo(val name: String, val type: ParameterType, val argument: NeoArgument<in Any,in Any>? = null)