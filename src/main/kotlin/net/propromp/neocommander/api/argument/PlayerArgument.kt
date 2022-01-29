package net.propromp.neocommander.api.argument

import org.bukkit.entity.Player

class PlayerArgument(name: String) :
    AbstractEntityArgument<Player>(name, SINGLE_PLAYER)