package me.zlaio.simpleshops.utils

import org.bukkit.ChatColor


fun translateColor(str: String): String {
    return ChatColor.translateAlternateColorCodes('&', str)
}