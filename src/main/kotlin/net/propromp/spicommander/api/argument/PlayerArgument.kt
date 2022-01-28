package net.propromp.spicommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.propromp.spicommander.api.NeoCommandContext
import net.propromp.spicommander.api.nms.NMSUtil
import org.bukkit.entity.Player

class PlayerArgument(name: String) : AbstractEntityArgument<Player>(name,SINGLE_PLAYER)