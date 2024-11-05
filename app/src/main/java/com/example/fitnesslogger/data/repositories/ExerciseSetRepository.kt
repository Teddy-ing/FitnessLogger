package com.example.fitnesslogger.data.repositories

import com.example.fitnesslogger.data.db.ExerciseDatabase
import com.example.fitnesslogger.data.db.entities.Exercise
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup

class ExerciseSetRepository(
    private val db :ExerciseDatabase
) {

    suspend fun upsert(exerciseSet : ExerciseSet) = db.getExerciseSetDao().upsert(exerciseSet)

    suspend fun delete(exerciseSet : ExerciseSet) = db.getExerciseSetDao().delete(exerciseSet)

    fun getALlExerciseSets() = db.getExerciseSetDao().getAllExerciseSets()

    fun getExerciseSetWithGroupByDate(date: String) = db.getExerciseSetDao().getExerciseSetWithGroupByDate(date)

    suspend fun getGroupForAdapter(date: String) = db.getExerciseSetDao().getGroupForAdapter(date)



}