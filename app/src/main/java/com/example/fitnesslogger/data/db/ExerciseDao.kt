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
    suspend fun upsert(item : Exercise): Long

    @Delete
    suspend fun delete(item : Exercise)

    @Query("SELECT * FROM exercise_table")
    fun getAllExercises() : LiveData<List<Exercise>>

    @Query("SELECT MAX(id) FROM exercise_table")
    fun getMaxId(): Int

}