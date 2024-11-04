package com.example.fitnesslogger.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.ExerciseApplication
import com.example.fitnesslogger.databinding.FragmentCalendarBinding
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
    private var selectedDate: LocalDate? = null //current date
    private lateinit var calendarAdapter: CalendarAdapter

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!


    override val di by lazy { (requireContext() as ExerciseApplication).di }
    private val factory: CalendarViewModelFactory by instance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedDate = LocalDate.now()


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
        setMonthView()

        //onClickListener for next month
        binding.btnNextMonth.setOnClickListener {
            selectedDate = selectedDate!!.plusMonths(1)
            setMonthView()
        }
        //same for prev
        binding.btnPrevMonth.setOnClickListener {
            selectedDate = selectedDate!!.minusMonths(1)
            setMonthView()
        }

    }

    private fun setMonthView() {
        monthYearText!!.text = monthYearFromDate(selectedDate)//sets month text
        val daysInMonth = daysInMonthArray(selectedDate)//gets the number of days in a month from

        //may change the structure and location of this later, but for I am initliazing the viewModel in here as its not needed in the construction of the RV
        //and fix it theres a fking bug
        val viewModel = ViewModelProvider(requireActivity(), factory)[CalendarViewModel::class.java]

        calendarAdapter = CalendarAdapter(daysInMonth, monthYearFromDateNoSpace(selectedDate), this, viewModel)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()//empty String list
        val yearMonth = YearMonth.from(date)//gets the year and month
        val daysInMonth = yearMonth.lengthOfMonth()//gets days in a month
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)//returns the current month with day as 1
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

    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMM yyyy")
        return date!!.format(formatter)
    }

    private fun monthYearFromDateNoSpace(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMyyyy")
        return date!!.format(formatter)
    }

    private fun monthFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMM")
        return date!!.format(formatter)
    }

    private fun dayMonthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("d MMM uuuu")
        return date!!.format(formatter)
    }

    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            val month = monthFromDate(selectedDate)
            val monthAndYear = monthYearFromDateNoSpace(selectedDate)
            //the dateField of an ExerciseSet will be dayMonth+monthAndYear




            val action = CalendarFragmentDirections.actionCalendarFragmentToExerciseFragment1(
                argPosition = position,
                argDayText = dayText!!,
                argMonth = month)

            //findNavController().navigate(action)


        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}