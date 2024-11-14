package com.example.fitnesslogger.ui.exercise_phase_2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository

@Suppress("UNCHECKED_CAST")
class ExerciseDialogViewModelFactory(
    private val exerciseRepository : ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExerciseDialogViewModel(exerciseRepository, exerciseSetRepository) as T
    }

}