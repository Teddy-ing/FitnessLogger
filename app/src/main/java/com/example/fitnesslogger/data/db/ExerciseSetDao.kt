package com.example.fitnesslogger.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup

@Dao
interface ExerciseSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(exerciseSet : ExerciseSet)

    @Delete
    suspend fun delete(exerciseSet : ExerciseSet)

    @Query("SELECT * FROM exercise_set_table")
    fun getAllExerciseSets(): LiveData<List<ExerciseSet>>

    //select is requesting all columns from ExSetTable and ExGroup from Ex. ExSetTable is the primary table, meaning the data is coming from this table
    //and we are joining it with another table to bring in more information
    //inner join specifies a join operation between exSetTable and exTable
    //On is the join condition, meaning that exSetTable exID must match the corrosponding id
    @Query("SELECT exercise_set_table.*, " + "exercise_table.exercise_group " +
            "FROM exercise_set_table " +
            "INNER JOIN exercise_table " +
            "ON exercise_set_table.exercise_set_id = exercise_table.id " +
            "WHERE exercise_set_date = :date LIMIT 1")
    suspend fun getExerciseSetWithGroupByDate(date: String): ExerciseSetWithGroup?


}