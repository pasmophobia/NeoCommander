package net.propromp.neocommander.api.argument

import org.bukkit.entity.Player

class PlayerListArgument(name: String): AbstractEntityArgument<List<Player>>(name, SEVERAL_PLAYERS)