package com.example.fitnesslogger.ui.exercise_phase_2

import androidx.lifecycle.ViewModel
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository

class ExerciseDialogViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {


}