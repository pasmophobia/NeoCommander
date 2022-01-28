package net.propromp.spicommander.api.annotation

import org.bukkit.command.CommandSender
import kotlin.reflect.KClass

/**
 * annotation of a command
 *
 * @property name
 * @property aliases
 * @property description
 * @property permission
 * @property senderType
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
@MustBeDocumented
annotation class Command(
    val name: String,
    val aliases: Array<String> = [],
    val description: String = "",
    val permission: String = "",
    val senderType: KClass<out CommandSender> = CommandSender::class
)