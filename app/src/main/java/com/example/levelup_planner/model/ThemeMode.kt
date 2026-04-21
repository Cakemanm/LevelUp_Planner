package com.example.levelup_planner.model

enum class ThemeMode(
    val displayName: String,
    val price: Int,
    val isDefault: Boolean,
    val isDark: Boolean
) {
    LIGHT("Light", 0, true, false),
    DARK("Dark", 0, true, true),
    OCEAN("Ocean", 50, false, false),
    FOREST("Forest", 50, false, false),
    SUNSET("Sunset", 50, false, true)
}
