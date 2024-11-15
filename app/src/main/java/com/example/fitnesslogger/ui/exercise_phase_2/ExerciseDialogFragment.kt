package com.example.fitnesslogger.ui.exercise_phase_2

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
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.databinding.FragmentExerciseDialogBinding
import org.kodein.di.DIAware
import org.kodein.di.instance

class ExerciseDialogFragment : Fragment(), DIAware {

    //similar to the other ExerciseSummary but just contains the reps weight and set count


    //safe args val
    private var exerciseId : Int? = null
    private var exerciseName : String? = null
    private var exerciseImage : Int? = null
    private var date : String? = null



    private var _binding: FragmentExerciseDialogBinding? = null
    private val binding get() = _binding!!

    //di
    override val di by lazy { (requireActivity().application as ExerciseApplication).di }
    private val factory : ExerciseDialogViewModelFactory by instance()
    private val viewModel : ExerciseDialogViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[ExerciseDialogViewModel::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //gets the exerciseId of item clicked
        val args : ExerciseDialogFragmentArgs by navArgs()
        exerciseId = args.argExerciseId
        exerciseName = args.argExerciseName
        exerciseImage = args.argExerciseImage
        date = args.argDate
        var set = 0



        //gets a list of all Sets for the selected exercise to be displayed in the RecyclcerView
        viewModel.getAllExerciseSetsByExerciseId(exerciseId!!).observe(viewLifecycleOwner, Observer { items ->

            //the current biggest set number+1 so that a new exerciseSet could be added with incrementing sets
            set = items.last().setCount+1


            val adapter = ExerciseSetAdapter(items, viewModel)
            binding.rvExercise.layoutManager = LinearLayoutManager(context)
            binding.rvExercise.adapter = adapter

        })


        binding.ibExercise.setImageResource(exerciseImage!!)
        binding.tvExerciseName.text = exerciseName

        binding.btnAddSet.setOnClickListener {
           val item = ExerciseSet(0, exerciseId!!, date!!, set, 0.0, 0.0)
           viewModel.upsertSet(item)


            viewModel.getAllExercises().observe(viewLifecycleOwner, Observer {
                it.forEach {
                    Log.d("taggy", "each exercise item son "+it.toString())
                }
            })

            viewModel.getAllExerciseSets().observe(viewLifecycleOwner, Observer {
                it.forEach {
                    Log.d("taggy", "each exercise set item son "+it.toString())
                }
            })
        }

    }

    private fun addSet() {
       // val setNumber = setsList.size + 1
      //  setsList.add(ExerciseSet(setNumber, 0.0, 0.0, exerciseName!!)) // Add an empty set with incrementing set number
      //  updateRecyclerView()
    }

    private fun updateRecyclerView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
