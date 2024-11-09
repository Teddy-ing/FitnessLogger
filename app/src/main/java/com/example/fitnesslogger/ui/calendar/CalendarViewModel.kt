package com.example.fitnesslogger.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {

    //this classes mutable liveData selectedDate val
    private var _selectedDate = MutableLiveData(LocalDate.now())
    //the varible that frag observes
    val selectedDate: LiveData<LocalDate> = _selectedDate


    suspend fun getGroupForAdapter(date: String):ExerciseSetWithGroup? {
        return exerciseSetRepository.getGroupForAdapter(date)
    }



    //function to create the proper month setup
    fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()//empty String list
        val yearMonth = YearMonth.from(date)//gets the year and month
        val daysInMonth = yearMonth.lengthOfMonth()//gets days in a month
        val firstOfMonth = _selectedDate.value!!.withDayOfMonth(1)//returns the current month with day as 1
        val dayOfWeek = firstOfMonth.dayOfWeek.value//gets the value of what first day of the month would be, ex monday =1
        for (i in 1..42) {//for when i =1 and goes until i = 42
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray//returns the month numbers
    }

    //functions to navigate to a new month
    fun nextMonth() {
        _selectedDate.value = _selectedDate.value!!.plusMonths(1)
    }

    fun prevMonth() {
        _selectedDate.value = _selectedDate.value!!.minusMonths(1)
    }

    //functions to return certain patterns of the selectedDate
    fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMM yyyy")
        return date!!.format(formatter)
    }

    fun monthYearFromDateNoSpace(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMyyyy")
        return date!!.format(formatter)
    }

    fun monthFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMM")
        return date!!.format(formatter)
    }


}