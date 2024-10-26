package com.example.fitnesslogger


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

        val dayOfMonth: TextView = binding.cellDayText



        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onItemListener.onItemClick(absoluteAdapterPosition, dayOfMonth.text as String)
        }
    }


    //inflates calenderCellBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val layoutParams = binding.root.layoutParams
        layoutParams.height = (parent.height * 0.166666666666).toInt()
        return CalendarViewHolder(binding, onItemListener)
    }

    //binds the day of the month to the textView
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.setText(daysOfMonth[position])
        holder.binding.cellDayText.post {
            Log.d(   "taggy","$position day ${holder.dayOfMonth.text}" )
        }
    }

    override fun getItemCount(): Int {

        return daysOfMonth.size
    }

    //in progress function to change the color of a day depending on what exercise group is chosen
    fun changeDayColor(position: Int, newColor: Int) {
      //  val viewHolder = findViewHolderForAdapterPosition(position)
     //   if (viewHolder != null) {
       //     viewHolder.dayOfMonth.setTextColor(newColor)



     //   }
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}