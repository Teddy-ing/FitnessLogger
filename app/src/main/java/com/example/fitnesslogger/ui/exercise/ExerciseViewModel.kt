package com.example.fitnesslogger.ui.exercise

import androidx.lifecycle.ViewModel
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {

    fun getAllExerciseSetsWithGroupByDate(date: String) = exerciseSetRepository.getAllExerciseSetsWithGroupByDate(date)

}