package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.nms.NMSUtil
import org.bukkit.entity.Player

class PlayerArgument(name: String) : AbstractEntityArgument<Player>(name,SINGLE_PLAYER)