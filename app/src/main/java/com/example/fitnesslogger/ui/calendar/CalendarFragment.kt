package com.example.fitnesslogger.ui.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.ExerciseApplication
import com.example.fitnesslogger.databinding.FragmentCalendarBinding
import com.example.fitnesslogger.ui.exercise.ExerciseViewModel
import com.example.fitnesslogger.ui.exercise.ExerciseViewModelFactory
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

//set binding
//then set calanderRV binding
//set month year text binding
//then get date
//then setMonthView

//set monthYearText

//create days in month val, its of daysInMonthArray method that takes
//in the current date

//



//getAll exerciseID?  in adapter, set color based on that potentially


//stretch goal, is to set text color for what type of exercise it is


class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener, DIAware {
    private var monthYearText: TextView? = null // create monthYearText set it to null
    private var calendarRecyclerView: RecyclerView? = null //create recycle view var
    private lateinit var calendarAdapter: CalendarAdapter

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!


    override val di by lazy { (requireActivity().application as ExerciseApplication).di }
    private val factory: CalendarViewModelFactory by instance()
    private val viewModel : CalendarViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[CalendarViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarRecyclerView = binding.rvCalendar
        monthYearText = binding.tvMonthYear//sets bindings

        //does necessary month setup by getting selectedDate from viewModel
        viewModel.selectedDate.observe(viewLifecycleOwner, Observer { selectedDate ->
            monthYearText!!.text = viewModel.monthYearFromDate(selectedDate)//sets month text
            val daysInMonth = viewModel.daysInMonthArray(selectedDate)//gets the number of days in a month from
            calendarAdapter = CalendarAdapter(daysInMonth, viewModel.monthYearFromDateNoSpace(selectedDate), this, this, viewModel)
            //passes in daysInMonth, month and year, onItemListener, and viewModel
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)
            calendarRecyclerView!!.layoutManager = layoutManager
            calendarRecyclerView!!.adapter = calendarAdapter
            selectedDate
        })

        //onClickListener for next month
        binding.btnNextMonth.setOnClickListener {
            viewModel.nextMonth()
        }
        //same for prev
        binding.btnPrevMonth.setOnClickListener {
            viewModel.prevMonth()
        }

    }

    override fun onItemClick(dayText: String?) {
        if (dayText != "") {

            //gets selectedDate from VM
            val selectedDate = viewModel.selectedDate.value

            val message = "Selected Date " + dayText + " " + viewModel.monthYearFromDate(selectedDate)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            val month = viewModel.monthFromDate(selectedDate)
            val monthAndYear = viewModel.monthYearFromDateNoSpace(selectedDate)
            //the dateField of an ExerciseSet will be dayMonth+monthAndYear


            //navigate to exerciseFragment with safe args
            val action = CalendarFragmentDirections.actionCalendarFragmentToExerciseFragment1(
                argDayText = dayText!!,
                argMonthAndYear = monthAndYear,
                argMonth = month)

            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}