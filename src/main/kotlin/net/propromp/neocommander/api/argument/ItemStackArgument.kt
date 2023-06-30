package net.propromp.neocommander.api.argument

import com.mojang.brigadier.arguments.ArgumentType
import net.minecraft.commands.arguments.item.ArgumentPredicateItemStack
import net.propromp.neocommander.api.NeoCommandContext
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class ItemStackArgument(name: String) : NeoArgument<Any, ItemStack>(name) {
    override fun asBrigadier() = net.minecraft.world.item.ItemStack.a as ArgumentType<out Any>

    override fun parse(context: NeoCommandContext, t: Any): ItemStack {
        val itemStack = (t as ArgumentPredicateItemStack).a(1, false)

        return CraftItemStack.asBukkitCopy(itemStack)
    }
}