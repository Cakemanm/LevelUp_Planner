package com.example.levelup_planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelup_planner.data.AppPreferences
import com.example.levelup_planner.model.AvatarChoice
import com.example.levelup_planner.model.ClassItem
import com.example.levelup_planner.model.ThemeMode
import com.example.levelup_planner.model.WorkItem
import com.example.levelup_planner.ui.screens.AddClassScreen
import com.example.levelup_planner.ui.screens.AddWorkScreen
import com.example.levelup_planner.ui.screens.ClassScreen
import com.example.levelup_planner.ui.screens.HomeScreen
import com.example.levelup_planner.ui.screens.OnboardingScreen
import com.example.levelup_planner.ui.screens.ProfileScreen
import com.example.levelup_planner.ui.screens.ShopScreen
import com.example.levelup_planner.ui.screens.ThemeSelectionScreen
import com.example.levelup_planner.ui.screens.WorkType
import com.example.levelup_planner.ui.theme.LevelUp_PlannerTheme

enum class AppScreen {
    ONBOARDING,
    THEME,
    HOME,
    ADD_CLASS,
    ADD_WORK,
    PROFILE,
    SHOP,
    CLASS_VIEW
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current

            var username by rememberSaveable {
                mutableStateOf(AppPreferences.getUsername(context))
            }

            var themeMode by rememberSaveable {
                mutableStateOf(AppPreferences.getThemeMode(context))
            }

            var avatarChoice by rememberSaveable {
                mutableStateOf(AppPreferences.getAvatar(context))
            }

            var newClassName by rememberSaveable {
                mutableStateOf("")
            }

            var newWorkName by rememberSaveable {
                mutableStateOf("")
            }

            var selectedWorkType by rememberSaveable {
                mutableStateOf(WorkType.CLASSWORK)
            }

            var currentScreen by rememberSaveable {
                mutableStateOf(
                    if (username.isBlank()) AppScreen.ONBOARDING else AppScreen.HOME
                )
            }

            var selectedClass by remember { mutableStateOf<ClassItem?>(null) }

            val classes = remember {
                mutableStateListOf<ClassItem>().apply {
                    addAll(AppPreferences.getClasses(context))
                }
            }

            val work = remember {
                mutableStateListOf<WorkItem>().apply {
                    addAll(AppPreferences.getWork(context))
                }
            }

            var points by rememberSaveable {
                mutableStateOf(AppPreferences.getPoints(context))
            }

            var newWorkDate by rememberSaveable { mutableStateOf("") }

            val ownedAvatars = remember {
                mutableStateMapOf<AvatarChoice, Boolean>().apply {
                    AppPreferences.getOwnedAvatars(context).forEach { put(it, true) }
                }
            }

            val ownedThemes = remember {
                mutableStateMapOf<ThemeMode, Boolean>().apply {
                    AppPreferences.getOwnedThemes(context).forEach { put(it, true) }
                }
            }

            // Award XP to a class and hand out 50 points per level-up
            val onTaskComplete: (ClassItem, Int) -> Unit = { targetClass, xpGain ->
                val index = classes.indexOf(targetClass)
                if (index != -1) {
                    var newXp = targetClass.xp + xpGain
                    var newLevel = targetClass.level
                    var earnedPoints = 0

                    while (newXp >= 100) {
                        newXp -= 100
                        newLevel++
                        earnedPoints += 50
                    }

                    classes[index] = targetClass.copy(xp = newXp, level = newLevel)
                    AppPreferences.saveClasses(context, classes.toList())

                    // Keep selectedClass reference in sync so the detail view stays accurate
                    if (selectedClass == targetClass) {
                        selectedClass = classes[index]
                    }

                    if (earnedPoints > 0) {
                        points += earnedPoints
                        AppPreferences.savePoints(context, points)
                    }
                }
            }

            val showBottomBar = currentScreen == AppScreen.HOME ||
                    currentScreen == AppScreen.PROFILE ||
                    currentScreen == AppScreen.SHOP

            LevelUp_PlannerTheme(
                themeMode = themeMode
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentScreen == AppScreen.HOME,
                                    onClick = { currentScreen = AppScreen.HOME },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = "Home"
                                        )
                                    },
                                    label = { Text("Home") }
                                )
                                NavigationBarItem(
                                    selected = currentScreen == AppScreen.SHOP,
                                    onClick = { currentScreen = AppScreen.SHOP },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.ShoppingCart,
                                            contentDescription = "Shop"
                                        )
                                    },
                                    label = { Text("Shop") }
                                )
                                NavigationBarItem(
                                    selected = currentScreen == AppScreen.PROFILE,
                                    onClick = { currentScreen = AppScreen.PROFILE },
                                    icon = {
                                        Image(
                                            painter = painterResource(id = avatarChoice.drawableRes),
                                            contentDescription = "Profile",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                        )
                                    },
                                    label = { Text("Profile") }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
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
                                val displayWork = work.filter { workItem ->
                                    !workItem.done && classes.any { it.name == workItem.className }
                                }.sortedWith(compareBy<WorkItem> { it.due.isEmpty() }.thenBy { it.due })

                                HomeScreen(
                                    username = username,
                                    classes = classes,
                                    work = displayWork,
                                    points = points,
                                    onAddClassClick = {
                                        newClassName = ""
                                        currentScreen = AppScreen.ADD_CLASS
                                    },
                                    onClassClick = { clickedClass ->
                                        selectedClass = clickedClass
                                        currentScreen = AppScreen.CLASS_VIEW
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

                            AppScreen.ADD_WORK -> {
                                AddWorkScreen(
                                    workName = newWorkName,
                                    onWorkNameChange = { newWorkName = it },
                                    selectedType = selectedWorkType,
                                    onTypeChange = { selectedWorkType = it },
                                    dueDate = newWorkDate,
                                    onDateChange = { newWorkDate = it },
                                    onSave = {
                                        val trimmed = newWorkName.trim()
                                        if (trimmed.isNotEmpty()) {
                                            work.add(
                                                WorkItem(
                                                    name = trimmed,
                                                    done = false,
                                                    xp = selectedWorkType.xpReward,
                                                    due = newWorkDate,
                                                    type = selectedWorkType,
                                                    className = selectedClass?.name ?: ""
                                                )
                                            )
                                            AppPreferences.saveWork(context, work.toList())
                                            newWorkName = ""
                                            newWorkDate = ""
                                            selectedWorkType = WorkType.CLASSWORK
                                            currentScreen = AppScreen.CLASS_VIEW
                                        }
                                    },
                                    onCancel = {
                                        newWorkName = ""
                                        newWorkDate = ""
                                        selectedWorkType = WorkType.CLASSWORK
                                        currentScreen = AppScreen.CLASS_VIEW
                                    }
                                )
                            }

                            AppScreen.PROFILE -> {
                                ProfileScreen(
                                    username = username,
                                    selectedTheme = themeMode,
                                    onThemeSelected = { selected ->
                                        themeMode = selected
                                        AppPreferences.saveThemeMode(context, selected)
                                    },
                                    selectedAvatar = avatarChoice,
                                    onAvatarSelected = { selected ->
                                        avatarChoice = selected
                                        AppPreferences.saveAvatar(context, selected)
                                    },
                                    ownedAvatars = ownedAvatars.keys,
                                    ownedThemes = ownedThemes.keys
                                )
                            }

                            AppScreen.SHOP -> {
                                ShopScreen(
                                    points = points,
                                    ownedAvatars = ownedAvatars.keys,
                                    ownedThemes = ownedThemes.keys,
                                    onPurchaseAvatar = { avatar ->
                                        if (avatar !in ownedAvatars && points >= avatar.price) {
                                            points -= avatar.price
                                            AppPreferences.savePoints(context, points)
                                            ownedAvatars[avatar] = true
                                            AppPreferences.saveOwnedAvatars(context, ownedAvatars.keys)
                                        }
                                    },
                                    onPurchaseTheme = { theme ->
                                        if (theme !in ownedThemes && points >= theme.price) {
                                            points -= theme.price
                                            AppPreferences.savePoints(context, points)
                                            ownedThemes[theme] = true
                                            AppPreferences.saveOwnedThemes(context, ownedThemes.keys)
                                        }
                                    }
                                )
                            }

                            AppScreen.CLASS_VIEW -> {
                                selectedClass?.let { currentClass ->
                                    ClassScreen(
                                        classItem = currentClass,
                                        workList = work.filter { it.className == currentClass.name && !it.done },

                                        onBack = { currentScreen = AppScreen.HOME },
                                        onCompleteWork = { clickedWork ->
                                            onTaskComplete(currentClass, clickedWork.xp)

                                            val workIndex = work.indexOf(clickedWork)
                                            if (workIndex != -1) {
                                                work[workIndex] = clickedWork.copy(done = true)
                                                AppPreferences.saveWork(context, work.toList())
                                            }
                                        },
                                        onAddWorkClick = {
                                            newWorkName = ""
                                            selectedWorkType = WorkType.CLASSWORK
                                            currentScreen = AppScreen.ADD_WORK
                                        },
                                        onDeleteWork = { workItem ->
                                            work.remove(workItem)
                                            AppPreferences.saveWork(context, work.toList())
                                        },
                                        onDeleteClass = {
                                            val classToDeleteName = currentClass.name

                                            classes.remove(currentClass)
                                            AppPreferences.saveClasses(context, classes.toList())

                                            work.removeAll { it.className == classToDeleteName }

                                            AppPreferences.saveWork(context, work.toList())

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
    }
}
