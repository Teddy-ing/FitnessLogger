package com.example.fitnesslogger.ui.exercise

data class ExerciseSummary(
    val exerciseId: Int,
    val exerciseName: String,
    val exerciseGroup: String,
    val exerciseImage : Int,
    var maxSetCount: Int
)