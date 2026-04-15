package com.example.levelup_planner.data

import android.R.attr.name
import android.R.attr.type
import android.content.Context
import com.example.levelup_planner.model.AvatarChoice
import com.example.levelup_planner.model.ClassItem
import com.example.levelup_planner.model.ThemeMode
import com.example.levelup_planner.model.WorkItem
import com.example.levelup_planner.ui.screens.WorkType
import org.json.JSONArray
import org.json.JSONObject

object AppPreferences {
    private const val PREFS_NAME = "levelup_prefs"
    private const val KEY_USERNAME = "username"
    private const val KEY_THEME = "theme"
    private const val KEY_CLASSES = "classes"
    private const val KEY_AVATAR = "avatar"
    private const val KEY_WORK = "work"
    private const val KEY_POINTS = "user_Points"


    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUsername(context: Context, username: String) {
        prefs(context).edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(context: Context): String {
        return prefs(context).getString(KEY_USERNAME, "") ?: ""
    }

    fun saveThemeMode(context: Context, themeMode: ThemeMode) {
        prefs(context).edit().putString(KEY_THEME, themeMode.name).apply()
    }

    fun getThemeMode(context: Context): ThemeMode {
        val saved = prefs(context).getString(KEY_THEME, ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(saved ?: ThemeMode.SYSTEM.name)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }

    fun saveAvatar(context: Context, avatar: AvatarChoice) {
        prefs(context).edit().putString(KEY_AVATAR, avatar.name).apply()
    }

    fun getAvatar(context: Context): AvatarChoice {
        val saved = prefs(context).getString(KEY_AVATAR, AvatarChoice.LIGHT.name)
        return try {
            AvatarChoice.valueOf(saved ?: AvatarChoice.LIGHT.name)
        } catch (e: Exception) {
            AvatarChoice.LIGHT
        }
    }

    fun saveClasses(context: Context, classes: List<ClassItem>) {
        val jsonArray = JSONArray()
        classes.forEach { classItem ->
            val obj = JSONObject()
            obj.put("name", classItem.name)
            obj.put("level", classItem.level)
            obj.put("xp", classItem.xp)
            jsonArray.put(obj)
        }

        prefs(context).edit().putString(KEY_CLASSES, jsonArray.toString()).apply()
    }

    fun getClasses(context: Context): List<ClassItem> {
        val jsonString = prefs(context).getString(KEY_CLASSES, "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        val classList = mutableListOf<ClassItem>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            classList.add(
                ClassItem(
                    name = obj.getString("name"),
                    level = obj.getInt("level"),
                    xp = obj.getInt("xp")
                )
            )
        }

        return classList
    }

    fun getWork(context: Context): List<WorkItem> {
        val jsonString = prefs(context).getString(KEY_WORK, "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        val workList = mutableListOf<WorkItem>()


        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)


            val name = obj.optString("name", "Unknown")
            val done = obj.optBoolean("done", false)
            val xp = obj.optInt("xp", 10)
            val due = obj.optString("due", "")

            val typeString = obj.optString("type", "CLASSWORK")
            val type = try {
                WorkType.valueOf(typeString)
            } catch (e: Exception) {
                WorkType.CLASSWORK
            }


            workList.add(
                WorkItem(
                    name = name,
                    done = done,
                    xp = xp,
                    due = due,
                    type = type
                )
            )
        }



        return workList
    }


    fun saveWork(context: Context, work: List<WorkItem>) {
        val jsonArray = JSONArray()
        work.forEach { workItem ->
            val obj = JSONObject()
            obj.put("name", workItem.name)
            obj.put("done", workItem.done)
            obj.put("xp", workItem.xp)
            obj.put("due", workItem.due)
            obj.put("type", workItem.type.name)

            jsonArray.put(obj)
        }


        prefs(context).edit().putString(KEY_WORK, jsonArray.toString()).apply()
    }

    fun getPoints(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_POINTS, 0)
    }

    fun savePoints(context: Context, points: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_POINTS, points).apply()
    }

}