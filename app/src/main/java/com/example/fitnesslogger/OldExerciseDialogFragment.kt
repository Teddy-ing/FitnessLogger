package com.example.fitnesslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesslogger.databinding.FragmentExerciseDialogBinding

class OldExerciseDialogFragment : Fragment() {

    private var exerciseImage: Int? = null
    var exerciseName: String? = null
    private lateinit var binding: FragmentExerciseDialogBinding
    private val setsList = mutableListOf<ExerciseSet>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseDialogBinding.inflate(inflater, container, false)
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
