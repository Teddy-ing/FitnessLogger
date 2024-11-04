package com.example.fitnesslogger.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository

@Suppress("UNCHECKED_CAST")
class CalendarViewModelFactory(
    private val exerciseRepository : ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarViewModel(exerciseRepository, exerciseSetRepository) as T
    }
}