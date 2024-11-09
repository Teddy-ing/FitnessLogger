package com.example.fitnesslogger.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesslogger.data.db.entities.Exercise
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {

    fun getAllExerciseSetsWithGroupByDate(date: String) = exerciseSetRepository.getAllExerciseSetsWithGroupByDate(date)

    //upsert function to add to the exercise table
    fun upsertExercise(item: Exercise): Int {
        val a = 0
        CoroutineScope(Dispatchers.Main).launch {
             exerciseRepository.upsert(item)
        }
        return a
    }


    fun upsertExerciseSet(item: ExerciseSet) = CoroutineScope(Dispatchers.Main).launch {
        exerciseSetRepository.upsert(item)
    }

    fun getMaxId(): Int = exerciseRepository.getMaxId()


    fun getAllExercises() = exerciseRepository.getALlExercises()

    fun getAllExerciseSets() = exerciseSetRepository.getALlExerciseSets()

}