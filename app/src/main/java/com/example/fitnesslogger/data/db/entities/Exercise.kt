package com.example.fitnesslogger.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "exercise_name")
    val name: String, // ex Benchpress
    @ColumnInfo(name = "exercise_group")
    val group: String // ex Chest, Biceps
)