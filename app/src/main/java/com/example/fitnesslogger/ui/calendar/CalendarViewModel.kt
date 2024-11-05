package com.example.fitnesslogger.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {

    suspend fun getGroupForAdapter(date: String):ExerciseSetWithGroup? {
        return exerciseSetRepository.getGroupForAdapter(date)
    }
}