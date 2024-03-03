package me.zlaio.simpleshops.utils

import com.google.gson.GsonBuilder

val gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()