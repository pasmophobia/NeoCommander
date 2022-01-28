package net.propromp.neocommander.api.annotation

import net.propromp.neocommander.api.argument.NeoArgument
import kotlin.reflect.KClass

/**
 * argument
 *
 * @property argumentClass class of the argument type
 */
@MustBeDocumented
annotation class Argument(
    val argumentClass: KClass<out NeoArgument<*,*>>
)
