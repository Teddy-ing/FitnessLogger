package com.example.fitnesslogger.data.repositories

import com.example.fitnesslogger.data.db.ExerciseDatabase
import com.example.fitnesslogger.data.db.entities.Exercise

class ExerciseRepository(
    private val db :ExerciseDatabase
) {

    suspend fun upsert(exercise : Exercise) = db.getExerciseDao().upsert(exercise)

    suspend fun delete(exercise : Exercise) = db.getExerciseDao().delete(exercise)

    fun getALlExercises() = db.getExerciseDao().getAllExercises()

}