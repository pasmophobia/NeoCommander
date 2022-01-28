package net.propromp.spicommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.propromp.spicommander.api.NeoCommandContext
import net.propromp.spicommander.api.nms.NMSUtil
import org.bukkit.inventory.ItemStack

class ItemStackArgument(name: String) : NeoArgument<Any, ItemStack>(name) {
    override fun asBrigadier() = NMSUtil.argumentItemStack().invokeStaticMethod("a") as ArgumentType<out Any>

    override fun parse(context: NeoCommandContext, t: Any): ItemStack {
        val itemStack = NMSUtil.argumentPredicateItemStack(t).invokeMethod("a", 1, false)!!
        return NMSUtil.craftItemStack().invokeStaticMethod("asBukkitCopy", itemStack) as ItemStack
    }
}