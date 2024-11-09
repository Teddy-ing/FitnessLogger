package com.example.fitnesslogger.data.repositories

import com.example.fitnesslogger.data.db.ExerciseDatabase
import com.example.fitnesslogger.data.db.entities.Exercise

class ExerciseRepository(
    private val db :ExerciseDatabase
) {

    suspend fun upsert(item : Exercise): Int {
        return db.getExerciseDao().upsert(item).toInt()//is wrapped in here so I can convert the long to an Int
    }

    suspend fun delete(item : Exercise) = db.getExerciseDao().delete(item)

    fun getALlExercises() = db.getExerciseDao().getAllExercises()

    fun getMaxId(): Int = db.getExerciseDao().getMaxId()

}