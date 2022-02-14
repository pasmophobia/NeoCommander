package net.propromp.neocommander.api.argument

import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.argument.annotation.ArgumentOf

/**
 * Argument that references another Argument
 *
 * @param T type used by brigadier argument
 * @param B type parsed
 * @constructor
 * constructor
 *
 * @param name name of the argument
 * @param clazz class of the other argument
 */
class ArgumentOf(name: String, val instance:NeoArgument<Any, Any>) : NeoArgument<Any, Any>(name) {
    override val suggestionProvider = instance.suggestionProvider

    constructor(name: String,annotation: ArgumentOf): this(name,annotation.clazz.java.getConstructor(String::class.java).newInstance(name))

    override fun asBrigadier() = instance.asBrigadier()

    override fun parse(context: NeoCommandContext, t: Any) = instance.parse(context, t)
}