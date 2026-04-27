package com.example.levelup_planner.model

import com.example.levelup_planner.ui.screens.WorkType


data class WorkItem(
    val name: String,
    val done: Boolean = false,
    val xp: Int,
    val due: String,
    val type: WorkType,
    val className: String
)
