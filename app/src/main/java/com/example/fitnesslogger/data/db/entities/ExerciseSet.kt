package com.example.fitnesslogger.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_set_table",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "exercise_set_id")
    val exerciseId: Int, // Foreign key linking to Exercise
    @ColumnInfo(name = "exercise_set_date")
    val date: String, // or use a Date type
    @ColumnInfo(name = "exercise_set_setcount")
    val setCount: Int,
    @ColumnInfo(name = "exercise_set_reps")
    val reps: Double,
    @ColumnInfo(name = "exercise_set_reps")
    val weight: Double
)