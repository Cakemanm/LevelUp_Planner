package com.example.levelup_planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.levelup_planner.data.AppPreferences
import com.example.levelup_planner.model.ClassItem
import com.example.levelup_planner.model.ThemeMode
import com.example.levelup_planner.ui.screens.AddClassScreen
import com.example.levelup_planner.ui.screens.HomeScreen
import com.example.levelup_planner.ui.screens.OnboardingScreen
import com.example.levelup_planner.ui.screens.ThemeSelectionScreen
import com.example.levelup_planner.ui.theme.LevelUp_PlannerTheme

enum class AppScreen {
    ONBOARDING,
    THEME,
    HOME,
    ADD_CLASS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val activity = LocalActivity.current

            var username by rememberSaveable {
                mutableStateOf(AppPreferences.getUsername(context))
            }

            var themeMode by rememberSaveable {
                mutableStateOf(AppPreferences.getThemeMode(context))
            }

            var newClassName by rememberSaveable {
                mutableStateOf("")
            }

            var currentScreen by rememberSaveable {
                mutableStateOf(
                    if (username.isBlank()) AppScreen.ONBOARDING else AppScreen.HOME
                )
            }

            val classes = remember {
                mutableStateListOf<ClassItem>().apply {
                    addAll(AppPreferences.getClasses(context))
                }
            }

            val useDarkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            LevelUp_PlannerTheme(
                darkTheme = useDarkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (currentScreen) {
                        AppScreen.ONBOARDING -> {
                            OnboardingScreen(
                                username = username,
                                onUsernameChange = { username = it },
                                onContinue = {
                                    val trimmed = username.trim()
                                    if (trimmed.isNotEmpty()) {
                                        username = trimmed
                                        AppPreferences.saveUsername(context, trimmed)
                                        currentScreen = AppScreen.THEME
                                    }
                                }
                            )
                        }

                        AppScreen.THEME -> {
                            ThemeSelectionScreen(
                                selectedTheme = themeMode,
                                onThemeSelected = { selected ->
                                    themeMode = selected
                                },
                                onConfirm = {
                                    AppPreferences.saveThemeMode(context, themeMode)
                                    currentScreen = AppScreen.HOME
                                }
                            )
                        }

                        AppScreen.HOME -> {
                            HomeScreen(
                                username = username,
                                classes = classes,
                                onAddClassClick = {
                                    newClassName = ""
                                    currentScreen = AppScreen.ADD_CLASS
                                }
                            )
                        }

                        AppScreen.ADD_CLASS -> {
                            AddClassScreen(
                                className = newClassName,
                                onClassNameChange = { newClassName = it },
                                onSave = {
                                    val trimmed = newClassName.trim()
                                    if (trimmed.isNotEmpty()) {
                                        classes.add(
                                            ClassItem(
                                                name = trimmed,
                                                level = 1,
                                                xp = 0
                                            )
                                        )
                                        AppPreferences.saveClasses(context, classes.toList())
                                        newClassName = ""
                                        currentScreen = AppScreen.HOME
                                    }
                                },
                                onCancel = {
                                    newClassName = ""
                                    currentScreen = AppScreen.HOME
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}