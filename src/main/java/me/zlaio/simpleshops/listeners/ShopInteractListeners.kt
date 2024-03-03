package me.zlaio.simpleshops.listeners

import me.zlaio.simpleshops.ShopData
import me.zlaio.simpleshops.createShopOwnerMenu
import me.zlaio.simpleshops.utils.gson
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.TileState
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

fun createPlayerInteractListener(validShopMaterials: Array<Material>, dataKey: NamespacedKey): Listener {
    return object : Listener {

        fun handleShopBlockInteract(
            shopTileState: TileState, player: Player, dataKey: NamespacedKey
        ) {
            val shopDataJson = shopTileState.persistentDataContainer.get(dataKey, PersistentDataType.STRING)
            val shopData = gson.fromJson(shopDataJson, ShopData::class.java)

            if (shopData._ownerUUID == player.uniqueId) {
                val menu = createShopOwnerMenu(shopData)
                player.openInventory(menu.inventory)
            } else {
                //Open client GUI
            }

        }

        @EventHandler
        fun onPlayerInteract(event: PlayerInteractEvent) {

            if (event.action != Action.RIGHT_CLICK_BLOCK) return
            val clickedBlock = event.clickedBlock ?: return
            if (!validShopMaterials.contains(clickedBlock.type)) return
            val tileState = clickedBlock.state as TileState
            if (!tileState.persistentDataContainer.has(dataKey)) return

            event.isCancelled = true

            handleShopBlockInteract(tileState, event.player, dataKey)
        }

    }


}