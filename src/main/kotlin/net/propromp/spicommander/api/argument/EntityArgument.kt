package net.propromp.spicommander.api.argument

import org.bukkit.entity.Entity

class EntityArgument(name: String): AbstractEntityArgument<Entity>(name, SINGLE_ENTITY)