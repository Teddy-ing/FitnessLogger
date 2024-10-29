package com.example.fitnesslogger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalendarFragment : Fragment() {
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









}