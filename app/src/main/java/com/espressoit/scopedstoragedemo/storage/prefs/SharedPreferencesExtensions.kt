package com.espressoit.scopedstoragedemo.storage.prefs

import android.content.SharedPreferences

internal fun SharedPreferences.write(key: String, value: Any?) {
    if (value == null) return

    this.edit().apply {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is HashSet<*> -> putStringSet(key, value as MutableSet<String>)
            else -> throw NoSuchMethodException("Unsupported type for shared preferences")
        }
    }.apply()
}
