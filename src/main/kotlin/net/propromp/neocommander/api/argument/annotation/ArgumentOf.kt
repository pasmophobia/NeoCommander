package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.ArgumentOf
import net.propromp.neocommander.api.argument.NeoArgument
import kotlin.reflect.KClass

@Argument(ArgumentOf::class)
annotation class ArgumentOf(val clazz: KClass<out NeoArgument<Any, Any>>)
