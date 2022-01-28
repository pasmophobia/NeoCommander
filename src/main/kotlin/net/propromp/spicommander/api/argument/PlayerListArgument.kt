package net.propromp.spicommander.api.argument

import org.bukkit.entity.Player

class PlayerListArgument(name: String): AbstractEntityArgument<List<Player>>(name, SEVERAL_PLAYERS)