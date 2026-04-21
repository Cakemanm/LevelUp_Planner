package com.example.levelup_planner.model

import com.example.levelup_planner.R

enum class AvatarChoice(
    val displayName: String,
    val drawableRes: Int,
    val price: Int,
    val isDefault: Boolean
) {
    LIGHT("Light", R.drawable.avatar_light, 0, true),
    DARK("Dark", R.drawable.avatar_dark, 0, true),
    OCEAN("Ocean", R.drawable.avatar_ocean, 50, false),
    FOREST("Forest", R.drawable.avatar_forest, 50, false),
    SUNSET("Sunset", R.drawable.avatar_sunset, 50, false)
}
