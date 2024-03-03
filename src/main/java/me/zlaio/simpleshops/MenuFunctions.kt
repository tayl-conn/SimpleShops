package me.zlaio.simpleshops

import me.zlaio.simpleshops.utils.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

//region General Items

val grayPaneFiller = ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
        .setDisplayName(" ")
        .build()

//endregion

//region Add Listing Menu

val sellSlotButton = {

    val icon = ItemBuilder(Material.RED_STAINED_GLASS_PANE)
        .setDisplayName("&c&lItem To Sell")
        .addLore(" ")
        .addLore("&cDrag and drop the item you wish to sell")
        .addLore("&conto this slot.")
        .build()

    val clickHandler = { event: InventoryClickEvent ->

        val currentItem = event.cursor

        if (currentItem?.type != Material.AIR) {
            val inventory = event.inventory
            inventory.setItem(event.slot, currentItem?.clone())
        }
    }

    Button(icon, clickHandler)
}

fun createAddListingMenu(): Menu {
    val menu =  Menu(3, "&7Creating Listing...")

    menu.setColumn(1, grayPaneFiller)
    menu.setColumn(5, grayPaneFiller)
    menu.setColumn(9, grayPaneFiller)

    menu.setButton(11, sellSlotButton())

    return menu
}

//endregion

//region Shop Owner Menu

val addListingIcon = ItemBuilder(Material.PAPER)
    .setDisplayName("&a+ &7Add Listing")
    .addLore(" ")
    .addLore("&7Click me to add an item to your shop!")
    .build()

fun addListingButton(): Button {
    val clickHandler: (InventoryClickEvent) -> Unit = { event: InventoryClickEvent ->
        val player = event.whoClicked as Player

        player.openInventory(createAddListingMenu().inventory)
    }

    return Button(addListingIcon, clickHandler)
}

fun createShopOwnerMenu(shopData: ShopData): Menu {
    val menu = Menu(6, "&a${shopData._ownerName}'s Shop")
    menu.setRow(6, grayPaneFiller)
    menu.setButton(49, addListingButton())
    return menu
}

//endregion