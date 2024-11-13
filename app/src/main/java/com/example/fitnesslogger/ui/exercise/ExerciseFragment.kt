package com.example.fitnesslogger.ui.exercise

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
import com.example.fitnesslogger.other.ExerciseLists
import com.example.fitnesslogger.databinding.FragmentExercise1Binding


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

class ExerciseFragment : Fragment(), DIAware, ExerciseChoiceAdapter.OnItemClickListener {

    data class ExerciseSummary( //data class that is utilized in the observer
        val exerciseId: Int,
        val exerciseName: String,
        val exerciseGroup: String,
        var maxSetCount: Int
    )


    //args, day and month passed in through safe args

    private var day : String? = null
    private var monthAndYear : String? = null
    private var month : String? = null



    //bindings
    private var _binding: FragmentExercise1Binding? = null
    private val binding get() = _binding!!

    //di
    override val di by lazy { (requireActivity().application as ExerciseApplication).di }
    private val factory: ExerciseViewModelFactory by instance()
    private val viewModel : ExerciseViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[ExerciseViewModel::class.java]
    }


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


        //var exerciseSummaries = mutableListOf<ExerciseSummary>()

        val args : ExerciseFragmentArgs by navArgs()

        day = args.argDayText
        monthAndYear = args.argMonthAndYear
        month = args.argMonth

        viewModel.getArguments(day!!, monthAndYear!!, month!!)

        //selected Exercise Adapter


        binding.tvTitle.text = "$month $day"

        //getAllEsetsOfDate.observe, update RV2 to show it.

        viewModel.getAllExerciseSetsWithGroupByDate(day+monthAndYear).observe(viewLifecycleOwner, Observer {items ->


                //first clears the title
                binding.tvTitle.text = "$month $day"

                val exerciseSet : LinkedHashSet<String> = LinkedHashSet()
                val exerciseSummaries = viewModel.getCurrentExercises(items)
                val adapter : SelectedExerciseAdapter

                //gets an ordered set of all exercise Groups that will then be turned into a list
                exerciseSummaries.forEach{
                    exerciseSet.add(it.exerciseGroup)

                }
                val curExerciseGroups = exerciseSet.toList()

                //sets the title to include the group in the title
                curExerciseGroups.forEachIndexed { index, group ->

                    if(index == curExerciseGroups.lastIndex && index != 0) {
                        //if theres a second or third unique exercise group, adds it onto the existing title.
                        binding.tvTitle.append("and $group")
                    } else if (index == curExerciseGroups.lastIndex && index != 0){
                        //if there is a total of 2 exercise groups write this line
                        binding.tvTitle.append(" and $group")
                    } else if(index == 0 ){
                        //if this is the first exercise in the list
                        binding.tvTitle.text = "$month $day $group"
                    } else {

                        binding.tvTitle.append(" $group ")
                    }
                    checkBoxes(group)
                }


            //    val adapter = SelectedExerciseAdapter(selectedExercises) { selectedExercise ->
            //        openExerciseFragment2(selectedExercise)
            //   }
            //

                //sets the adapter with the current items
                adapter = SelectedExerciseAdapter()
                binding.rvFragment2.layoutManager = LinearLayoutManager(context)
                binding.rvFragment2.adapter = adapter

                binding.rvFragment2.visibility = View.VISIBLE

                        //adapter call, (curExerciseName, curBiggestSets, curExerciseIds)
                        // adapter only needs to know for each item, the exName and exSets and eID
                        // on Click of an item, it will get All Items of eID.
                 //   Log.d("taggy", exerciseSummaries.toString())
                Log.d("taggy", curExerciseGroups.toString())

        })

        binding.btnAddExercise.setOnClickListener {
            //function to replace RecyclerView2, which houses the users existing exercises with RecylcerView1, which shows the complete list of all available exercises to choose from
            populateRV1()

        }



        // Ensure RecyclerView2 is visible if there are selected exercises
        //if (selectedExercises.isNotEmpty()) {
         //   updateRecyclerView2Adapter()
      //      binding.rvFragment2.visibility = View.VISIBLE
       // } else {
       //     binding.rvFragment2.visibility = View.GONE
       // }
    }

    private fun populateRV1() {

        val exerciseList = mutableListOf<Pair<Int, String>>()


        val exerciseListObj = ExerciseLists() //object to get the existing exercises

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
            "Chest"     -> binding.cbChest.isChecked     = true
            "Back"      -> binding.cbBack.isChecked      = true
            "Shoulders" -> binding.cbShoulders.isChecked = true
            "Biceps"    -> binding.cbBiceps.isChecked    = true
            "Triceps"   -> binding.cbTriceps.isChecked   = true
            "Legs"      -> binding.cbLegs.isChecked      = true
            "Abs"       -> binding.cbAbs.isChecked       = true
        //forearms

        }

    }

    //sets up adapter and layout manager for rv1
    private fun updateRecyclerView1(exerciseList : List<Pair<Int, String>>) {
       val adapter = ExerciseChoiceAdapter(exerciseList, this)
        binding.rvFragment1.layoutManager = LinearLayoutManager(context)
        binding.rvFragment1.adapter = adapter
        binding.rvFragment2.visibility = View.GONE
        binding.rvFragment1.visibility = View.VISIBLE

    }

    //onItemCLick for ExerciseChoiceAdapter, to add an exerciseSet entry to the database
    //and display it in Recylcer View 2
    override fun onItemClick(position: Int, exercise: Pair<Int, String>, selectedGroup : String) {
        //exercise meaning the chosen exercise, currentGroup meaning
        //the muscle group of said exercise
        viewModel.upsertExerciseAndExerciseSet(exercise, selectedGroup)
        binding.rvFragment1.visibility = View.GONE



        viewModel.getAllExercises().observe(this, Observer {
            it.forEach {
                Log.d("taggy", "each exercise item son "+it.toString())
            }
        })

        viewModel.getAllExerciseSets().observe(this, Observer {
            it.forEach {
                Log.d("taggy", "each exercise set item son "+it.toString())
            }
        })


    }

    //adds an exerciwse to the selected exercise list and updates the second rv
    private fun updateRecyclerView2(exercise: Pair<Int, String>) {
       // selectedExercises.add(exercise)
        updateRecyclerView2Adapter()

    }

    //sets up the adapter and layout manager for rv2
    private fun updateRecyclerView2Adapter() {

    }

    //opens fragment2
    private fun openExerciseFragment2(exercise: Pair<Int, String>) {
      //  val fragment = ExerciseFragment2.newInstance(exercise.first, exercise.second)
     //   parentFragmentManager.beginTransaction()
     //       .replace(R.id.flFragment, fragment)
     //       .addToBackStack(null)
     //       .commit()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}

