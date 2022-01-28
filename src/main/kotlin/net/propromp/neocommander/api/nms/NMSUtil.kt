package net.propromp.neocommander.api.nms

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.chat.ComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object NMSUtil {
    val packageName: String

    init {
        packageName = Bukkit.getServer().javaClass.`package`.name.split(".").last()
    }

    fun getClass(name: String) = NMS(Class.forName(name))
    fun getNMSClass(name: String) = getClass("net.minecraft.server.$packageName.$name")
    fun getCraftBukkitClass(name: String) = getClass("org.bukkit.craftbukkit.$packageName.$name")
    fun getClass(name: String, instance: Any) = NMS(Class.forName(name), instance)
    fun getNMSClass(name: String, instance: Any) = getClass("net.minecraft.server.$packageName.$name", instance)
    fun getCraftBukkitClass(name: String, instance: Any) =
        getClass("org.bukkit.craftbukkit.$packageName.$name", instance)

    fun craftServer() = getCraftBukkitClass("CraftServer", Bukkit.getServer())
    fun dedicatedPlayerList() = getNMSClass("DedicatedPlayerList", craftServer().invokeMethod("getHandle")!!)
    fun dedicatedServer() = getNMSClass("DedicatedServer", dedicatedPlayerList().invokeMethod("getServer")!!)
    fun minecraftServer() = getNMSClass("MinecraftServer")
    fun commandListenerWrapper() = getNMSClass("CommandListenerWrapper")
    fun iChatBaseComponent(instance: Any) = getNMSClass("IChatBaseComponent", instance)
    fun iChatBaseComponent() = getNMSClass("IChatBaseComponent")
    fun iChatBaseComponentChatSerializer() = NMS(iChatBaseComponent().clazz.classes[0])
    fun minecraftDispatcher() = getNMSClass(
        "CommandDispatcher",
        dedicatedServer().getField("vanillaCommandDispatcher", clazz = minecraftServer().clazz)!!
    )
    fun argumentEntity(isSingle:Boolean,isPlayer:Boolean) = getNMSClass("ArgumentEntity").apply {
        constructBySelectingTypes(
            arrayOf(Boolean::class.javaPrimitiveType!!,Boolean::class.javaPrimitiveType!!),
            arrayOf(isSingle,isPlayer)
        )
    }
    fun vanillaCommandWrapper() = getCraftBukkitClass("command.VanillaCommandWrapper")
    fun simpleCommandMap() = getClass("org.bukkit.command.SimpleCommandMap", craftServer().getField("commandMap")!!)
    fun entityPlayer() = getNMSClass("EntityPlayer")
    fun entityPlayer(instance: Any) = getNMSClass("EntityPlayer",instance)
    fun entity() = getNMSClass("Entity")
    fun entity(instance: Any) = getNMSClass("Entity",instance)
    fun entitySelector(instance: Any) = getNMSClass("EntitySelector",instance)
    fun craftPlayer(player: Player) = getCraftBukkitClass("entity.CraftPlayer", player)
    fun argumentPosition() = getNMSClass("ArgumentPosition")
    fun argumentVec3() = getNMSClass("ArgumentVec3")
    fun iVectorPosition() = getNMSClass("IVectorPosition")
    fun iVectorPosition(instance: Any) = getNMSClass("IVectorPosition",instance)
    fun baseBlockPosition() = getNMSClass("BaseBlockPosition")
    fun baseBlockPosition(instance: Any) = getNMSClass("BaseBlockPosition",instance)
    fun world() = getNMSClass("World")
    fun world(instance: Any) = getNMSClass("World",instance)
    fun vec3d() = getNMSClass("Vec3D")
    fun vec3d(instance: Any) = getNMSClass("Vec3D",instance)
    fun argumentPredicateItemStack() = getNMSClass("ArgumentPredicateItemStack")
    fun argumentPredicateItemStack(instance: Any) = getNMSClass("ArgumentPredicateItemStack",instance)
    fun argumentItemStack() = getNMSClass("ArgumentItemStack")
    fun argumentItemStack(instance: Any) = getNMSClass("ArgumentItemStack",instance)
    fun itemStack() = getNMSClass("ItemStack")
    fun itemStack(instance: Any) = getNMSClass("ItemStack",instance)
    fun craftItemStack() = getCraftBukkitClass("inventory.CraftItemStack")

    fun TextComponent.toIChatBaseComponent() = iChatBaseComponent(
        iChatBaseComponentChatSerializer().invokeStaticMethod(
            "a",
            ComponentSerializer.toString(this)
        )!!
    )

    fun Component.toIChatBaseComponent() = iChatBaseComponent(
        iChatBaseComponentChatSerializer().invokeStaticMethod(
            "a",
            PlainComponentSerializer.plain().serialize(this)
        )!!
    )

    fun Player.toEntityPlayer() = entityPlayer().also { it.instance = craftPlayer(this).invokeMethod("getHandle")!! }
}