package com.example.fitnesslogger.ui.exercise

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesslogger.ExerciseApplication
import com.example.fitnesslogger.ExerciseLists
import com.example.fitnesslogger.R
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup
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

        var items : List<ExerciseSetWithGroup?> = listOf()
        val curExerciseGroups : MutableList<String> = mutableListOf()
        val curExercisesWithSets : MutableList<ExerciseSetWithGroup?> = mutableListOf()
        val curExerciseIds : MutableList<Int> = mutableListOf()
        val curBiggestSets : MutableList<Int> = mutableListOf()
        var curSet : Int = 0
        var curExerciseNames : MutableList<String> = mutableListOf()

        day = args.argDayText
        monthAndYear = args.argMonthAndYear
        month = args.argMonth

        binding.tvTitle.text = "$month $day"

        val viewModel = ViewModelProvider(requireActivity(), factory)[ExerciseViewModel::class.java]

        //getAllEsetsOfDate.observe, update RV2 to show it.

        viewModel.getAllExerciseSetsWithGroupByDate(day+monthAndYear).observe(viewLifecycleOwner, Observer {
            items = it
            //ex data
            // PReacher biceps set 1  eID 1  ID 1
            // PReacher biceps set 2  eID 1  ID 2
            // PReacher biceps set 3  eID 1  ID 3
            // PReacher biceps set 4  eID 1  ID 4

            // hammer curls set 1  eID 2  ID 5
            // hammer curls set 2  eID 2  ID 6
            // hammer curls set 3  eID 2  ID 7

            //tricep pushdown set 1 eID 3 id 8
            items.forEach { item ->
                if (item != null) {
                    if (item.exerciseSet.exerciseId !in curExerciseIds) { //if new eID
                        curExerciseIds += item.exerciseSet.exerciseId
                        curExerciseNames += item.name
                        if(item.group !in curExerciseGroups) {
                            curExerciseGroups += item.group

                        }

                        if(curSet!= 0) {
                            curBiggestSets += curSet
                            curSet = 0
                        }

                        curSet++
                    } else {
                        curSet++
                    }
                }
            }
            if(curSet != 0) {
                curBiggestSets += curSet
                //index 2 = 1
                curSet = 0
            }

            curExerciseGroups.forEachIndexed { index, group ->
                if(index == curExerciseGroups.lastIndex) {
                    binding.tvTitle.text = "and $group"
                    checkBoxes(group)
                } else {
                    binding.tvTitle.text = " $group "
                    checkBoxes(group)
                }
            }



            //adapter call, (curExerciseName, curBiggestSets, curExerciseIds)
            // adapter only needs to know for each item, the exName and exSets and eID
            // on Click of an item, it will get All Items of eID.



        })

        //on press of a SET: does a query, gets All entities of eID



        items.forEach() {
            //for Each Exercise Set(which is items)

            // title.text = GROUP

            // then if items.index > 0 && it != items[0]
            // title.text = " and " GROUP


            //then call set checkBoxes(group : String)




        }
        //then update RV2 with the items



        //binding.isChecked = true to set it to true


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
    //function to set each checkbox that is of the same exercise group to true
    private fun checkBoxes (group : String) {

        when (group) {
            "chest"     -> binding.cbChest.isChecked     = true
            "back"      -> binding.cbBack.isChecked      = true
            "shoulders" -> binding.cbShoulders.isChecked = true
            "biceps"    -> binding.cbBiceps.isChecked    = true
            "triceps"   -> binding.cbTriceps.isChecked   = true
            "legs"      -> binding.cbLegs.isChecked      = true
            "abs"       -> binding.cbAbs.isChecked       = true
        //forearms

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