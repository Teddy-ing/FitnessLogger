package com.example.fitnesslogger.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesslogger.ExerciseApplication
import com.example.fitnesslogger.other.ExerciseLists
import com.example.fitnesslogger.databinding.FragmentExercise1Binding
import com.example.fitnesslogger.ui.calendar.CalendarAdapter
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

class ExerciseFragment1 : Fragment(), DIAware, ExerciseChoiceAdapter.OnItemClickListener {

    data class ExerciseSummary( //data class that is utilized in the observer
        val exerciseId: Int,
        val exerciseName: String,
        val exerciseGroup: String,
        var maxSetCount: Int
    )


    //args, day and month passed in through safe args
    private val args : ExerciseFragment1Args by navArgs()
    private var day : String? = null
    private var monthAndYear : String? = null
    private var month : String? = null


    private val selectedExercises = mutableListOf<Pair<Int, String>>()

    //bindings
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

        val curExerciseGroups : MutableList<String> = mutableListOf()
        val exerciseSummaries = mutableListOf<ExerciseSummary>()

        day = args.argDayText
        monthAndYear = args.argMonthAndYear
        month = args.argMonth

        binding.tvTitle.text = "$month $day"

        val viewModel = ViewModelProvider(requireActivity(), factory)[ExerciseViewModel::class.java]

        //getAllEsetsOfDate.observe, update RV2 to show it.

        viewModel.getAllExerciseSetsWithGroupByDate(day+monthAndYear).observe(viewLifecycleOwner, Observer {items ->
            val biggestSets = mutableMapOf<Int, Int>() // exerciseId -> set count
            items.forEach { item ->
                    val exerciseId = item.exerciseSet.exerciseId
                    //gets the current eID
                    biggestSets[exerciseId] = (biggestSets[exerciseId] ?: 0) + 1
                    //if the current Id doesnt exist in the map, assigns it to the next avaiable index. then for each loop iteration increments set val

                    //if its a new eID
                    if (biggestSets[exerciseId] == 1) {
                        exerciseSummaries.add(
                            ExerciseSummary(
                                exerciseId = exerciseId,
                                exerciseName = item.name,
                                exerciseGroup = item.group,
                                maxSetCount = 1
                            )
                        )
                    } else {
                        // Update max set count
                        exerciseSummaries.find { it.exerciseId == exerciseId }?.maxSetCount = biggestSets[exerciseId]!!
                    }
            }

            curExerciseGroups.forEachIndexed { index, group ->
                if(index == curExerciseGroups.lastIndex) {
                    binding.tvTitle.text = "and $group"
                } else {
                    binding.tvTitle.text = " $group "
                }
                checkBoxes(group)
            }

            //adapter call, (curExerciseName, curBiggestSets, curExerciseIds)
            // adapter only needs to know for each item, the exName and exSets and eID
            // on Click of an item, it will get All Items of eID.

        })

        //exerciseList.clear()



        binding.btnAddExercise.setOnClickListener {
            //function to replace RV2, which houses the users existing exercises with RV1, which shows the complete list of all available exercises to choose from
            populateRV1()


        }



        // Ensure RecyclerView2 is visible if there are selected exercises
        if (selectedExercises.isNotEmpty()) {
            updateRecyclerView2Adapter()
            binding.rvFragment2.visibility = View.VISIBLE
        } else {
            binding.rvFragment2.visibility = View.GONE
        }
    }

    private fun populateRV1() {
        binding.rvFragment2.visibility = View.GONE
        val exerciseList = mutableListOf<Pair<Int, String>>()


        val exerciseListObj = ExerciseLists() //object to get the existing exercises

        //exerciseList.clear()


        //put the following if statements in another method.

        //on liveData reset,

        if (binding.cbChest.isChecked){
            exerciseList.addAll(exerciseListObj.chest)
        }


        if(binding.cbBack.isChecked) {
            exerciseList.addAll(exerciseListObj.back)
        }

        if(binding.cbShoulders.isChecked) {
            exerciseList.addAll(exerciseListObj.shoulders)
        }

        if(binding.cbBiceps.isChecked) {
            exerciseList.addAll(exerciseListObj.biceps)
        }

        if(binding.cbTriceps.isChecked) {
            exerciseList.addAll(exerciseListObj.triceps)
        }

        if(binding.cbLegs.isChecked) {
            exerciseList.addAll(exerciseListObj.legs)
        }

        if(binding.cbAbs.isChecked) {
            exerciseList.addAll(exerciseListObj.abs)
        }
        /*if(binding.cbForeArms.isChecked) {
                binding.tvTitle2.text = " Forearms"
                onCheckboxColor(R.color.green)
                exerciseList.addAll(exerciseListObj.foreArms)
            } */

        updateRecyclerView1(exerciseList)
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
    private fun updateRecyclerView1(exerciseList : List<Pair<Int, String>>) {
       val adapter = ExerciseChoiceAdapter(exerciseList, object: OnItemListener {
            val a = 1
       })
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