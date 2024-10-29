package com.example.fitnesslogger


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class TempFile : AppCompatActivity(), CalendarAdapter.OnItemListener, ExerciseFragment1.OnDayColorChangeListener {
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
    }

    private fun initWidgets() {

        calendarRecyclerView = binding.calendarRecyclerView
        monthYearText = binding.monthYearTV
    }

    //sets the current month to the view
    private fun setMonthView() {
        monthYearText!!.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }


    //method to get days in month returns arraylist
    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }


    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date!!.format(formatter)
    }

    private fun monthFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM")
        return date!!.format(formatter)
    }


    fun previousMonthAction(view: View?) {
        selectedDate = selectedDate!!.minusMonths(1)
        setMonthView()
    }


    fun nextMonthAction(view: View?) {
        selectedDate = selectedDate!!.plusMonths(1)
        setMonthView()
    }


    //onClick for the days
    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
            // monthFromDate(selectedDate)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            var month = monthFromDate(selectedDate)
            //define a new instance of exerciseFramgent1
            val exerciseFragment1 = ExerciseFragment1.newInstance(dayText!!, position, month)

            //replace calenderView with fragment1
           // supportFragmentManager.beginTransaction().apply {
             //   replace(R.id.flFragment, exerciseFragment1)
            //    addToBackStack(null)
           //     binding.llContainer.visibility = View.GONE
            //    commit()
           // }
        }

       // supportFragmentManager.addOnBackStackChangedListener {
        //    if (supportFragmentManager.backStackEntryCount == 0) {
        //        binding.llContainer.visibility = View.VISIBLE // Restore visibility of the existing layout
        //    }
      //  }
    }

    override fun onDayColorChange(position: Int, newColor: Int) {
        calendarAdapter.changeDayColor(position, newColor)
    }

}