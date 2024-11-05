package com.example.fitnesslogger.ui.exercise

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesslogger.ExerciseApplication
import com.example.fitnesslogger.ExerciseLists
import com.example.fitnesslogger.R
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup
import com.example.fitnesslogger.databinding.FragmentCalendarBinding
import com.example.fitnesslogger.databinding.FragmentExercise1Binding
import com.example.fitnesslogger.ui.calendar.CalendarViewModel
import com.example.fitnesslogger.ui.calendar.CalendarViewModelFactory
import org.kodein.di.DIAware
import org.kodein.di.instance

//flow chart

//gets date

//searches ExerciseSets with that date

/////might do:  all exerciseSets with matching dates are put into items[]

/////in item, for each unique ExerciseID put its ExerciseGroup into curExerciseGroup[]

/////adds group to title as string.  If another group exists, add it to title with and.

//then not using the item[], or maybe using it idk, populate the RV based on exercise ID

//for RV, gets the highest ID of current exercise ID, and uses that to display the name and set count

//somehow get the image from it, perhaps match exercise Name IG

//do the following logic.

//then do the logic on clicking an exercise from add Exercise

//

class ExerciseFragment1 : Fragment(), DIAware {


    //args, day and month passed in through safe args
    private val args : ExerciseFragment1Args by navArgs()
    private var day : String? = null
    private var monthAndYear : String? = null
    private var month : String? = null


    private var items : List<ExerciseSetWithGroup?> = listOf()
    private var curExerciseGroup : List<String> = listOf()
    private val exerciseList = mutableListOf<Pair<Int, String>>()
    private val selectedExercises = mutableListOf<Pair<Int, String>>()

    private var _binding: FragmentExercise1Binding? = null
    private val binding get() = _binding!!

    //di
    override val di by lazy { (requireActivity().application as ExerciseApplication).di }
    private val factory: ExerciseViewModelFactory by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercise1Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        day = args.argDayText
        monthAndYear = args.argMonthAndYear
        month = args.argMonth

        val viewModel = ViewModelProvider(requireActivity(), factory)[ExerciseViewModel::class.java]

        items.forEach() {

            //if first do this

            // else add it with and
        }



        //binding.isChecked = true to set it to true

        binding.tvTitle.text = "$month $day"
        exerciseList.clear()
        val exerciseListObj = ExerciseLists() //object to get the existing exercises


        binding.btnAddExercise.setOnClickListener {
            binding.rvFragment2.visibility = View.GONE
            //exerciseList.clear()


            //put the following if statements in another method.

            //on liveData reset,

            if (binding.cbChest.isChecked){
                binding.tvTitle2.text = " Chest"
                exerciseList.addAll(exerciseListObj.chest)
                }


            if(binding.cbBack.isChecked) {
                binding.tvTitle2.text = " Back"
                exerciseList.addAll(exerciseListObj.back)
            }

            if(binding.cbShoulders.isChecked) {
                binding.tvTitle2.text = " Shoulders"
                exerciseList.addAll(exerciseListObj.shoulders)
            }

            if(binding.cbBiceps.isChecked) {
                binding.tvTitle2.text = " Biceps"
                exerciseList.addAll(exerciseListObj.biceps)
            }

            if(binding.cbTriceps.isChecked) {
                binding.tvTitle2.text = " Triceps"
                exerciseList.addAll(exerciseListObj.triceps)
            }

            if(binding.cbLegs.isChecked) {
                binding.tvTitle2.text = " Legs"
                exerciseList.addAll(exerciseListObj.legs)
            }

            if(binding.cbAbs.isChecked) {
                binding.tvTitle2.text = " Abs"
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}