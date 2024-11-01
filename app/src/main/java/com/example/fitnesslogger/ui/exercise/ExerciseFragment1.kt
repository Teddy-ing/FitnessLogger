package com.example.fitnesslogger.ui.exercise

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesslogger.ExerciseLists
import com.example.fitnesslogger.R
import com.example.fitnesslogger.databinding.FragmentExercise1Binding

class ExerciseFragment1 : Fragment() {
    //method to change daycolor
    interface OnDayColorChangeListener {
        fun onDayColorChange(position: Int, newColor: Int)
    }

    private var selectedDate: String? = null
    private var dayPosition: Int? = null
    private var selectedMonth: String? = null
    private var listener: OnDayColorChangeListener? = null
    private lateinit var binding: FragmentExercise1Binding
    private val exerciseList = mutableListOf<Pair<Int, String>>()
    private val selectedExercises = mutableListOf<Pair<Int, String>>()

    //initializes fragment and sets up a fragment result listner for fragment2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDate = it.getString(ARG_SELECTED_DATE)
            dayPosition = it.getInt(ARG_DAY_POSITION)
            selectedMonth = it.getString(ARG_SELECTED_MONTH)
        }

        parentFragmentManager.setFragmentResultListener(
            ExerciseFragment2.REQUEST_KEY,
            this
        ) { _, bundle ->
            val exerciseName = bundle.getString(ExerciseFragment2.EXTRA_EXERCISE_NAME)
            val setsCount = bundle.getInt(ExerciseFragment2.EXTRA_SETS_COUNT)
            // Update RecyclerView2 with the number of sets done
            selectedExercises.find { it.second == exerciseName }?.let {
                // Update the item with the number of sets done

                Log.d("ExerciseFragment1", "Exercise: $exerciseName, Sets: $setsCount")
            }
            binding.rvFragment2.visibility = View.VISIBLE
        }
    }

    //implements onDayColorChange
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDayColorChangeListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnDayColorChangeListener")
        }
    }

    //Clears listener
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    //inflates view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercise1Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = "$selectedMonth $selectedDate"
        exerciseList.clear()
        val exerciseListObj = ExerciseLists() //object to get the existing exercises


        binding.btnAddExercise.setOnClickListener {
            binding.rvFragment2.visibility = View.GONE
            //exerciseList.clear()
            if (binding.cbChest.isChecked){
                binding.tvTitle2.text = " Chest"
                onCheckboxColor(R.color.red)
                exerciseList.addAll(exerciseListObj.chest)
                }


            if(binding.cbBack.isChecked) {
                binding.tvTitle2.text = " Back"
                onCheckboxColor(R.color.purple)
                exerciseList.addAll(exerciseListObj.back)
            }

            if(binding.cbShoulders.isChecked) {
                binding.tvTitle2.text = " Shoulders"
                onCheckboxColor(R.color.pink)
                exerciseList.addAll(exerciseListObj.shoulders)
            }

            if(binding.cbBiceps.isChecked) {
                binding.tvTitle2.text = " Biceps"
                onCheckboxColor(R.color.yellow)
                exerciseList.addAll(exerciseListObj.biceps)
            }

            if(binding.cbTriceps.isChecked) {
                binding.tvTitle2.text = " Triceps"
                onCheckboxColor(R.color.orange)
                exerciseList.addAll(exerciseListObj.triceps)
            }

            if(binding.cbLegs.isChecked) {
                binding.tvTitle2.text = " Legs"
                onCheckboxColor(R.color.blue)
                exerciseList.addAll(exerciseListObj.legs)
            }

            if(binding.cbAbs.isChecked) {
                binding.tvTitle2.text = " Abs"
                onCheckboxColor(R.color.cyan)
                exerciseList.addAll(exerciseListObj.abs)
            }



            /*if(binding.cbForeArms.isChecked) {
                binding.tvTitle2.text = " Forearms"
                onCheckboxColor(R.color.green)
                exerciseList.addAll(exerciseListObj.foreArms)
            } */




            updateRecyclerView1()
        }

        // Ensure RecyclerView2 is visible if there are selected exercises
        if (selectedExercises.isNotEmpty()) {
            updateRecyclerView2Adapter()
            binding.rvFragment2.visibility = View.VISIBLE
        } else {
            binding.rvFragment2.visibility = View.GONE
        }
    }

    //sets up adapter and layout manager for rv1
    private fun updateRecyclerView1() {
        val adapter = ExerciseChoiceAdapter(exerciseList) { exercise ->
            updateRecyclerView2(exercise)
        }
        binding.rvFragment1.layoutManager = LinearLayoutManager(context)
        binding.rvFragment1.adapter = adapter
    }

    //adds an exerciwse to the selected exercise list and updates the second rv
    private fun updateRecyclerView2(exercise: Pair<Int, String>) {
        selectedExercises.add(exercise)
        updateRecyclerView2Adapter()
        binding.rvFragment1.visibility = View.GONE
        binding.rvFragment2.visibility = View.VISIBLE
    }

    //sets up the adapter and layout manager for rv2
    private fun updateRecyclerView2Adapter() {
        val adapter = ExerciseChoiceAdapter2(selectedExercises) { selectedExercise ->
            openExerciseFragment2(selectedExercise)
        }
        binding.rvFragment2.layoutManager = LinearLayoutManager(context)
        binding.rvFragment2.adapter = adapter
    }

    //opens fragment2
    private fun openExerciseFragment2(exercise: Pair<Int, String>) {
    //    val fragment = ExerciseFragment2.newInstance(exercise.first, exercise.second)
    //    parentFragmentManager.beginTransaction()
         //   .replace(R.id.flFragment, fragment)
    //        .addToBackStack(null)
   //         .commit()
    }


    private fun onCheckboxColor(newColor: Int) {
        listener?.onDayColorChange(dayPosition ?: return, newColor)
    }

    companion object {
        const val ARG_SELECTED_DATE = "selected_date"
        const val ARG_DAY_POSITION = "day_position"
        const val ARG_SELECTED_MONTH = "selected_month"

        fun newInstance(selectedDate: String, dayPosition: Int, selectedMonth: String): ExerciseFragment1 {
            val fragment = ExerciseFragment1()
            val args = Bundle()
            args.putString(ARG_SELECTED_DATE, selectedDate)
            args.putInt(ARG_DAY_POSITION, dayPosition)
            args.putString(ARG_SELECTED_MONTH, selectedMonth)
            fragment.arguments = args
            return fragment
        }
    }
}