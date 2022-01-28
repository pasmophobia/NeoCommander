package net.propromp.neocommander.api.util

import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import net.propromp.neocommander.api.nms.NMS
import net.propromp.neocommander.api.nms.NMSUtil

fun <S> CommandNode<S>.removeCommand(name: String) {
    val nms = NMS(CommandNode::class.java,this)
    val children = nms.getField("children") as MutableMap<String, CommandNode<S>>
    val literals = nms.getField("literals") as MutableMap<String, LiteralCommandNode<S>>
    val arguments = nms.getField("arguments") as MutableMap<String, ArgumentCommandNode<S, *>>
    children.remove(name)
    literals.remove(name)
    arguments.remove(name)
}