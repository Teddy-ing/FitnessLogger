package com.example.fitnesslogger

import com.example.fitnesslogger.ui.exercise_phase_2.ExerciseSet
import org.junit.Assert.assertEquals
import org.junit.Test

class ExerciseSetTest {

    @Test
    fun testExerciseSetCreation() {
        val exerciseSet = ExerciseSet(setNumber = 1, weight = 50.0, reps = 12.0, exerciseName = "Bench Press")
        assertEquals(1, exerciseSet.setNumber)
        assertEquals(50.0, exerciseSet.weight, 0.0)
        assertEquals(12.0, exerciseSet.reps, 0.0)
        assertEquals("Bench Press", exerciseSet.exerciseName)
    }

    @Test
    fun testExerciseSetUpdate() {
        val exerciseSet = ExerciseSet(setNumber = 1, weight = 50.0, reps = 12.0, exerciseName = "Bench Press")
        exerciseSet.weight = 55.0
        exerciseSet.reps = 10.0
        assertEquals(55.0, exerciseSet.weight, 0.0)
        assertEquals(10.0, exerciseSet.reps, 0.0)
    }
}