package com.example.fitnesslogger


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.ActivityMainBinding
import com.example.fitnesslogger.ui.calendar.CalendarAdapter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class OldMainActivity : AppCompatActivity(){
    private var monthYearText: TextView? = null // create monthYearText set it to null
    private var calendarRecyclerView: RecyclerView? = null //create recycle view var
    private var selectedDate: LocalDate? = null //current date
    private lateinit var binding : ActivityMainBinding
    private lateinit var calendarAdapter: CalendarAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// do this for binding
        setContentView(binding.root)
        initWidgets()//call initialize widgets
        selectedDate = LocalDate.now()//sets selectedDate to current date so it starts at current
        setMonthView() // set the view
       // binding.
    }

    private fun initWidgets() {

      //  calendarRecyclerView = binding.RVCalendar
      //  monthYearText = binding.monthYearTV
    }

    //sets the current month to the view
    private fun setMonthView() {
        monthYearText!!.text = monthYearFromDate(selectedDate)//sets month text
        val daysInMonth = daysInMonthArray(selectedDate)//gets the number of days in a month from function
        //calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }


    //method to get days in month returns arraylist
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


    //fun previousMonthAction(view: View?) {
     //   selectedDate = selectedDate!!.minusMonths(1)
    //    setMonthView()
   // }


   // fun nextMonthAction(view: View?) {
     //   selectedDate = selectedDate!!.plusMonths(1)
     //   setMonthView()
   // }


    //onClick for the days
    //was ovverride
   fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
             monthFromDate(selectedDate)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            var month = monthFromDate(selectedDate)
            //define a new instance of exerciseFramgent1
         //   val exerciseFragment1 = ExerciseFragment1.newInstance(dayText!!, position, month)

        //    replace calenderView with fragment1
        //    supportFragmentManager.beginTransaction().apply {
        //        replace(R.id.flFragment, exerciseFragment1)
        //        addToBackStack(null)
         //       binding.llContainer.visibility = View.GONE
       //         commit()
        //    }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
       //        binding.llContainer.visibility = View.VISIBLE // Restore visibility of the existing layout
            }
        }
    }

   // override fun onDayColorChange(position: Int, newColor: Int) {
       // calendarAdapter.changeDayColor(position, newColor)
    //}

}