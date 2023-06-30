package net.propromp.neocommander.api.util

import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode


fun <S> CommandNode<S>.removeCommand(name: String) {
    val children = this::class.java.getField("children").apply {
        isAccessible = true
    }.get(this) as MutableMap<String, CommandNode<S>>
    val literals = this::class.java.getField("literals").apply {
        isAccessible = true
    }.get(this) as MutableMap<String, LiteralCommandNode<S>>
    val arguments = this::class.java.getField("arguments").apply {
        isAccessible = true
    }.get(this) as MutableMap<String, ArgumentCommandNode<S, *>>
    children.remove(name)
    literals.remove(name)
    arguments.remove(name)
}