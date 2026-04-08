package com.example.levelup_planner.model


data class WorkItem(
    val name: String,
    val done: Boolean = false,
    val xp: Int = 5,
    val due: String
)
