package com.example.fitnesslogger.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnesslogger.data.db.entities.ExerciseSet

@Dao
interface ExerciseSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(exerciseSet : ExerciseSet)

    @Delete
    suspend fun delete(exerciseSet : ExerciseSet)

    @Query("SELECT * FROM exercise_set_table")
    fun getAllExerciseSets(): LiveData<List<ExerciseSet>>


}