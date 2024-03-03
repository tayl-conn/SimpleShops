package me.zlaio.simpleshops.listeners

import me.zlaio.simpleshops.Menu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

val shopInventoryListener = object : Listener {

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.inventory.holder !is Menu)
            return

        val menu = event.inventory.holder as Menu
        menu._eventHandler.onMenuOpen(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.holder !is Menu)
            return

        val menu = event.inventory.holder as Menu
        menu._eventHandler.onMenuClose(event)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.clickedInventory?.holder !is Menu)
            return

        event.isCancelled = true

        val menu = event.clickedInventory?.holder as Menu
        menu.handleClickEvent(event)
    }

}