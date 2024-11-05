package com.example.fitnesslogger.data.db.results

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.fitnesslogger.data.db.entities.ExerciseSet

data class ExerciseSetWithGroup(  //a query result that joins the two tables, returns the Exercise group or name string based on ExerciseSet ID
    @Embedded val exerciseSet : ExerciseSet,
    @ColumnInfo(name = "exercise_group")
    val group : String,
    @ColumnInfo(name = "exercise_name")
    val name : String
)