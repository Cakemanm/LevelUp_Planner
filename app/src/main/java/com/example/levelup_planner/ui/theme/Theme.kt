package com.example.levelup_planner.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.levelup_planner.model.ThemeMode

fun themeSwatchColor(theme: ThemeMode): Color = when (theme) {
    ThemeMode.LIGHT -> Color(0xFF6650A4)
    ThemeMode.DARK -> Color(0xFFD0BCFF)
    ThemeMode.OCEAN -> Color(0xFF0D47A1)
    ThemeMode.FOREST -> Color(0xFF1B5E20)
    ThemeMode.SUNSET -> Color(0xFFBF360C)
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val OceanColorScheme = darkColorScheme(
    primary = OceanPrimary,
    onPrimary = OceanBackground,
    primaryContainer = OceanSurfaceVariant,
    onPrimaryContainer = OceanOnDark,
    secondary = OceanSecondary,
    onSecondary = OceanBackground,
    secondaryContainer = OceanSurfaceVariant,
    onSecondaryContainer = OceanOnDark,
    tertiary = OceanTertiary,
    onTertiary = OceanBackground,
    background = OceanBackground,
    onBackground = OceanOnDark,
    surface = OceanSurface,
    onSurface = OceanOnDark,
    surfaceVariant = OceanSurfaceVariant,
    onSurfaceVariant = OceanOnDark
)

private val ForestColorScheme = darkColorScheme(
    primary = ForestPrimary,
    onPrimary = ForestBackground,
    primaryContainer = ForestSurfaceVariant,
    onPrimaryContainer = ForestOnDark,
    secondary = ForestSecondary,
    onSecondary = ForestBackground,
    secondaryContainer = ForestSurfaceVariant,
    onSecondaryContainer = ForestOnDark,
    tertiary = ForestTertiary,
    onTertiary = ForestBackground,
    background = ForestBackground,
    onBackground = ForestOnDark,
    surface = ForestSurface,
    onSurface = ForestOnDark,
    surfaceVariant = ForestSurfaceVariant,
    onSurfaceVariant = ForestOnDark
)

private val SunsetColorScheme = darkColorScheme(
    primary = SunsetPrimary,
    onPrimary = SunsetBackground,
    primaryContainer = SunsetSurfaceVariant,
    onPrimaryContainer = SunsetOnDark,
    secondary = SunsetSecondary,
    onSecondary = SunsetBackground,
    secondaryContainer = SunsetSurfaceVariant,
    onSecondaryContainer = SunsetOnDark,
    tertiary = SunsetTertiary,
    onTertiary = SunsetBackground,
    background = SunsetBackground,
    onBackground = SunsetOnDark,
    surface = SunsetSurface,
    onSurface = SunsetOnDark,
    surfaceVariant = SunsetSurfaceVariant,
    onSurfaceVariant = SunsetOnDark
)

@Composable
fun LevelUp_PlannerTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.OCEAN -> OceanColorScheme
        ThemeMode.FOREST -> ForestColorScheme
        ThemeMode.SUNSET -> SunsetColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
