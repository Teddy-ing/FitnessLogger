package com.example.fitnesslogger.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import com.example.fitnesslogger.ui.calendar.CalendarViewModel

@Suppress("UNCHECKED_CAST")
class ExerciseViewModelFactory(
    private val exerciseRepository : ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExerciseViewModel(exerciseRepository, exerciseSetRepository) as T
    }

}