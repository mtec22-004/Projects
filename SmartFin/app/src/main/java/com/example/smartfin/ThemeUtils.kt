package com.example.smartfin

import android.content.Context

object ThemeUtils {
    private const val PREFS_NAME = "theme_prefs"
    private const val THEME_KEY = "current_theme"

    fun saveTheme(context: Context, isDarkMode: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putBoolean(THEME_KEY, isDarkMode)
            apply()
        }
    }

    fun loadTheme(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(THEME_KEY, false) // Default to light theme
    }
}
