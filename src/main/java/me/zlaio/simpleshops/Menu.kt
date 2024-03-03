package me.zlaio.simpleshops

import me.zlaio.simpleshops.utils.DEFAULT_MENU_EVENT_HANDLER
import me.zlaio.simpleshops.utils.DEFAULT_MENU_ROWS
import me.zlaio.simpleshops.utils.translateColor
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

interface MenuEventHandler {
    fun onMenuOpen(event: InventoryOpenEvent) {}
    fun onMenuClose(event: InventoryCloseEvent) {}
}

data class Button(val icon: ItemStack, val clickHandler: (InventoryClickEvent) -> Unit)

class Menu
    : InventoryHolder
{

    private var _buttonMap: HashMap<Int, (InventoryClickEvent) -> Unit>
    private var _inventory: Inventory
    var _eventHandler: MenuEventHandler

    constructor(rows: Int, title: String, eventHandler: MenuEventHandler) {
        _inventory = Bukkit.createInventory(this, rows * 9, translateColor(title))
        this._eventHandler = eventHandler
        _buttonMap = HashMap()
    }

    constructor(rows: Int, title: String): this(rows, title, DEFAULT_MENU_EVENT_HANDLER)
    constructor(title: String): this(DEFAULT_MENU_ROWS, title)
    constructor(): this("")

    fun setItem(slot: Int, item: ItemStack) {
        _inventory.setItem(slot, item)
    }

    fun setButton(slot: Int, button: Button) {
        _buttonMap[slot] = button.clickHandler
        _inventory.setItem(slot, button.icon)
    }

    fun handleClickEvent(event: InventoryClickEvent) {
        _buttonMap[event.slot]?.invoke(event)
    }

    fun setRow(row: Int, item: ItemStack) {
        for (i in (row - 1) * 9 until row * 9)
            setItem(i, item)
    }

    fun setColumn(column: Int, item: ItemStack) {
        for (i in 0 until _inventory.size / 9)
           setItem((column - 1) + (i * 9), item)
    }

    override fun getInventory(): Inventory {
        return _inventory
    }

}