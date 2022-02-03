package net.propromp.neocommander.api.argument

internal class EnumArgumentImpl<T:Enum<*>>(name: String, clazz: Class<T>): EnumArgument<T>(name,clazz)