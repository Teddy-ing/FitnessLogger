package com.example.fitnesslogger.ui.calendar


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.R
import com.example.fitnesslogger.databinding.CalendarCellBinding
import kotlinx.coroutines.launch


//thoughts

//set up a ViewModel in Calendar Fragment

//This RV doesnt need to have access to the database items, as in I dont need pass in the current items.
//^^ I wont make an observe thing because I will make each day regardless if theres a database element

//I will pass into the RV adapter the month, and year.

//then in onBind, it will


class CalendarAdapter(//constructor to initlize these two vars
    private val daysOfMonth: ArrayList<String>,//the specific day of a dayButton in calendar
    private val monthAndYear: String,//corresponding mmmYYYY
    private val onItemListener: OnItemListener,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: CalendarViewModel,
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    //constructor sets up a click listener on the root
    inner class CalendarViewHolder(val binding : CalendarCellBinding, private val onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        val dayOfMonth: TextView = binding.tvCellDay
        //dayOfMonth is of type TextView and is a reference to the TVcellDay

        init {
            binding.root.setOnClickListener(this)//sets the onclickListener on the root meaning the entire cell can be clicked on
        }

        override fun onClick(view: View) {//sets the onClickListener functionality after gathering information from onBindViewHolder
            //each button already has its correct value
            onItemListener.onItemClick(dayOfMonth.text as String)
        }
    }


    //inflates calenderCellBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //calendarcell binding
        val layoutParams = binding.root.layoutParams//
        layoutParams.height = (parent.height * 0.166666666666).toInt()//sets the hieght of calendarCell to exactly 1/6 of the rv
        return CalendarViewHolder(binding, onItemListener)
    }

    //binds the day of the month to the textView
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        //takes in the created ViewHolder innerClass
        holder.dayOfMonth.text = daysOfMonth[position]
        //if(holder.dayOfMonth.text != "") {
        //holder.binding.cellDayBackground.foreground = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_border)}

        //check if an exercise Exists for this date, and if so, change the text color to the matching exercise

        //this should be able to set the color to the specific right thingy ma bob
        lifecycleOwner.lifecycleScope.launch {
            val currentGroup = viewModel.getGroupForAdapter(daysOfMonth[position]+monthAndYear)
            if (currentGroup != null) {

                Log.d(   "taggy","Exercise Group: ${currentGroup.group} and also ${daysOfMonth[position]+monthAndYear}" )
            }
        }

    }

    override fun getItemCount(): Int {

        return daysOfMonth.size
    }


    interface OnItemListener {
        fun onItemClick(dayText: String?)
    }
}