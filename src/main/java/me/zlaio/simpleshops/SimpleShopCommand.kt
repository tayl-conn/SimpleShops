package me.zlaio.simpleshops

import me.zlaio.simpleshops.utils.gson
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.TileState
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

fun simpleShopCommand(validShopMaterials: Array<Material>, dataKey: NamespacedKey): CommandExecutor {
    return CommandExecutor { sender, _, _, args ->


        if (sender !is Player) {
            println("/simpleshops is meant for in-game use only.")
            return@CommandExecutor false
        }


        if (args.isEmpty()) {
            sender.sendMessage("Usage: /simpleshops <create | delete>")
            return@CommandExecutor false
        }

        when (args[0]) {
            "create" -> {
                val targetBlock = sender.getTargetBlockExact(3)

                if (targetBlock == null) {
                    sender.sendMessage("Please make sure you are looking at the block you wish to make into a shop.")
                    return@CommandExecutor false
                }

                if (!validShopMaterials.contains(targetBlock.type)) {
                    sender.sendMessage("You cannot make a shop out of this type of block.")
                    return@CommandExecutor false
                }

                val tileState = targetBlock.state as TileState

                if (tileState.persistentDataContainer.has(dataKey)) {
                    sender.sendMessage("A shop has already been assigned to this block.")
                    return@CommandExecutor false
                }

                val shopDataJson = gson.toJson(ShopData(sender.uniqueId, sender.displayName))
                tileState.persistentDataContainer.set(dataKey, PersistentDataType.STRING, shopDataJson)
                tileState.update()

                sender.sendMessage("Created shop!")
            }

            "delete" -> TODO("Not Yet Implemented")
            else -> {
                sender.sendMessage("Usage: /simpleshops <create | delete>")
            }
        }

        false
    }

}