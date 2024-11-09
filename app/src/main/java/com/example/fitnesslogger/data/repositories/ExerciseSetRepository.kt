package com.example.fitnesslogger.data.repositories

import androidx.lifecycle.LiveData
import com.example.fitnesslogger.data.db.ExerciseDatabase
import com.example.fitnesslogger.data.db.entities.Exercise
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup

class ExerciseSetRepository(
    private val db :ExerciseDatabase
) {

    suspend fun upsert(item : ExerciseSet) = db.getExerciseSetDao().upsert(item)

    suspend fun delete(item : ExerciseSet) = db.getExerciseSetDao().delete(item)

    fun getALlExerciseSets() = db.getExerciseSetDao().getAllExerciseSets()

    fun getExerciseSetWithGroupByDate(date: String) = db.getExerciseSetDao().getExerciseSetWithGroupByDate(date)

    fun getAllExerciseSetsWithGroupByDate(date: String) = db.getExerciseSetDao().getAllExerciseSetsWithGroupByDate(date)

    suspend fun getGroupForAdapter(date: String) = db.getExerciseSetDao().getGroupForAdapter(date)



}