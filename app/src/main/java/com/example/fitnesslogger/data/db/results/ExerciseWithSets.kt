package com.example.fitnesslogger.data.db.results

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fitnesslogger.data.db.entities.Exercise
import com.example.fitnesslogger.data.db.entities.ExerciseSet

data class ExerciseWithSets(  //a one to many relationship between exercise and ExerciseSet - designed to get 1 exercise and all the ExerciseSets associated with it
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val sets: List<ExerciseSet>
)






//val exerciseName = exerciseWithSets.exercise.name
//    val exerciseGroup = exerciseWithSets.exercise.group
//
//    // Access each ExerciseSet associated with this Exercise
//    exerciseWithSets.sets.forEach { set ->
//        val setDate = set.date
//        val setCount = set.setCount
//        val reps = set.reps
//        val weight = set.weight
//        // Use these values in the UI
//    }



//example of iterating through a list of the sets


//For each Exercise found, Room then uses the @Relation in ExerciseWithSets to look up all ExerciseSet rows in exercise_set_table where exerciseId matches the id of the Exercise.