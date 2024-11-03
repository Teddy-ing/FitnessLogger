package com.example.fitnesslogger.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnesslogger.data.db.entities.Exercise

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(exercise : Exercise)

    @Delete
    suspend fun delete(exercise : Exercise)

    @Query("SELECT * FROM exercise_table")
    fun getAllExercises() : LiveData<List<Exercise>>



}