package net.propromp.neocommander.api.argument

import org.bukkit.entity.Entity

class EntityArgument(name: String): AbstractEntityArgument<Entity>(name, SINGLE_ENTITY)