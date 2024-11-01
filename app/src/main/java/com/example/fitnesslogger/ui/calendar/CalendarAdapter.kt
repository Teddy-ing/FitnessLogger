package com.example.fitnesslogger.ui.calendar


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.CalendarCellBinding


class CalendarAdapter(//constructor to initlize these two vars
    private val daysOfMonth: ArrayList<String>,
    private val onItemListener: OnItemListener,
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    //constructor sets up a click listener on the root
    inner class CalendarViewHolder(val binding : CalendarCellBinding, private val onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        val dayOfMonth: TextView = binding.tvCellDay
        //dayOfMonth is a reference to the cellDayText element

        init {
            binding.root.setOnClickListener(this)//sets the onclickListener on the root meaning the entire cell can be clicked on
        }

        override fun onClick(view: View) {//sets the onClickListener functionality after gathering information from onBindViewHolder
            //each button already has its correct value
            onItemListener.onItemClick(absoluteAdapterPosition, dayOfMonth.text as String)
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
        holder.binding.tvCellDay.post {
            Log.d(   "taggy","$position day ${holder.dayOfMonth.text}" )
        }
    }

    override fun getItemCount(): Int {

        return daysOfMonth.size
    }

    //in progress function to change the color of a day depending on what exercise group is chosen
    fun changeDayColor(position: Int, newColor: Int) {
        //val viewHolder = findViewHolderForAdapterPosition(position)
       // if (viewHolder != null) {
         //   viewHolder.dayOfMonth.setTextColor(newColor)



      //  }
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}