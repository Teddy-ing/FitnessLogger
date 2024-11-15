package com.example.fitnesslogger.ui.exercise_phase_2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesslogger.data.db.entities.Exercise
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseDialogViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {




    //database functions
    fun getAllExerciseSetsByExerciseId(id : Int) = exerciseSetRepository.getAllExerciseSetsByExerciseId(id)

    fun upsertSet(item : ExerciseSet) = viewModelScope.launch {
        exerciseSetRepository.upsert(item)
    }

    fun getAllExercises() = exerciseRepository.getALlExercises()

    fun getAllExerciseSets() = exerciseSetRepository.getALlExerciseSets()



}