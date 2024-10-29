package com.example.fitnesslogger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.FragmentCalendarBinding
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




class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener {
    private var monthYearText: TextView? = null // create monthYearText set it to null
    private var calendarRecyclerView: RecyclerView? = null //create recycle view var
    private var selectedDate: LocalDate? = null //current date
    private lateinit var calendarAdapter: CalendarAdapter
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

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
        calendarRecyclerView = binding.RVCalendar
        monthYearText = binding.TVMonthYear//sets bindings
        setMonthView()

    }

    private fun setMonthView() {
        monthYearText!!.text = monthYearFromDate(selectedDate)//sets month text
        val daysInMonth = daysInMonthArray(selectedDate)//gets the number of days in a month from function
        calendarAdapter = CalendarAdapter(daysInMonth, this)
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
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date!!.format(formatter)
    }

    private fun monthFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM")
        return date!!.format(formatter)
    }

    override fun onItemClick(position: Int, dayText: String?) {
      //  if (dayText != "") {
        //    val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
       //     monthFromDate(selectedDate)
         //   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        //    var month = monthFromDate(selectedDate)
            //define a new instance of exerciseFramgent1
         //   val exerciseFragment1 = ExerciseFragment1.newInstance(dayText!!, position, month)

        //    replace calenderView with fragment1
        ////            supportFragmentManager.beginTransaction().apply {
       //                 replace(R.id.flFragment, exerciseFragment1)
       //                 addToBackStack(null)
       //                 binding.llContainer.visibility = View.GONE
       //                 commit()
       //             }
      //  }

      //  supportFragmentManager.addOnBackStackChangedListener {
       //     if (supportFragmentManager.backStackEntryCount == 0) {
       //         binding.llContainer.visibility = View.VISIBLE // Restore visibility of the existing layout
      //      }
       // }
    }

    //todo : set listners in onView for month buttons
    //set up navigation to the next fragment

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}