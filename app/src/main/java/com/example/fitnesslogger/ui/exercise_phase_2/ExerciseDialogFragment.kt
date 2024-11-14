package com.example.fitnesslogger.ui.exercise_phase_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesslogger.ExerciseApplication
import com.example.fitnesslogger.databinding.FragmentExerciseDialogBinding
import org.kodein.di.DIAware
import org.kodein.di.instance

class ExerciseDialogFragment : Fragment(), DIAware {

    //safe args val
    private var exerciseId : Int? = null



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



        binding.ibExercise.setImageResource(exerciseImage!!)
        binding.tvExerciseName.text = exerciseName

        binding.btnAddSet.setOnClickListener {
            addSet()
        }

        updateRecyclerView()
    }

    private fun addSet() {
        val setNumber = setsList.size + 1
        setsList.add(ExerciseSet(setNumber, 0.0, 0.0, exerciseName!!)) // Add an empty set with incrementing set number
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        val adapter = ExerciseSetAdapter(setsList)
        binding.rvExercise.layoutManager = LinearLayoutManager(context)
        binding.rvExercise.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
