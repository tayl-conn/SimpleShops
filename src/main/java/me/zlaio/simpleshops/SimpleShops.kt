package me.zlaio.simpleshops

import me.zlaio.simpleshops.listeners.createPlayerInteractListener
import me.zlaio.simpleshops.listeners.shopInventoryListener
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin

class SimpleShops : JavaPlugin() {

    override fun onEnable() {

        val validShopMaterials = Array(1) {
            Material.CHEST
        }

        val dataKey = NamespacedKey(this, "shopData")
        getCommand("simpleshops")?.setExecutor(simpleShopCommand(validShopMaterials, dataKey))

        server.pluginManager.registerEvents(shopInventoryListener, this)
        server.pluginManager.registerEvents(createPlayerInteractListener(validShopMaterials, dataKey), this)
    }

}