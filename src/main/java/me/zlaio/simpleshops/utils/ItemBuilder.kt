package me.zlaio.simpleshops.utils

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemBuilder(material: Material) {

    private val _itemStack: ItemStack
    private val _itemMeta: ItemMeta?
    private val _loreLines: ArrayList<String>

    init {
        _itemStack = ItemStack(material)
        _itemMeta = _itemStack.itemMeta
        _loreLines = ArrayList()
    }

    fun addLore(line: String): ItemBuilder {
        _loreLines.add(translateColor(line))
        return this
    }

    fun setDisplayName(displayName: String): ItemBuilder {
        _itemMeta?.setDisplayName(translateColor(displayName))
        return this
    }

    fun build(): ItemStack {
        _itemMeta?.lore = _loreLines
        _itemStack.itemMeta = _itemMeta
        return _itemStack
    }

}