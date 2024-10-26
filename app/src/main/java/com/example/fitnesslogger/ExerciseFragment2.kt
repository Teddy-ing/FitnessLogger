package com.example.fitnesslogger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesslogger.databinding.FragmentExercise2Binding

class ExerciseFragment2 : Fragment() {

    private var exerciseImage: Int? = null
    var exerciseName: String? = null
    private lateinit var binding: FragmentExercise2Binding
    private val setsList = mutableListOf<ExerciseSet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            exerciseImage = it.getInt(ARG_EXERCISE_IMAGE)
            exerciseName = it.getString(ARG_EXERCISE_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercise2Binding.inflate(inflater, container, false)
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

    override fun onDetach() {
        super.onDetach()
        val result = Bundle().apply {
            putString(EXTRA_EXERCISE_NAME, exerciseName)
            putInt(EXTRA_SETS_COUNT, setsList.size)
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY, result)
    }

    companion object {
        private const val ARG_EXERCISE_IMAGE = "exercise_image"
        private const val ARG_EXERCISE_NAME = "exercise_name"

        const val REQUEST_KEY = "exercise_fragment_2_request"
        const val EXTRA_EXERCISE_NAME = "exercise_name"
        const val EXTRA_SETS_COUNT = "sets_count"

        fun newInstance(exerciseImage: Int, exerciseName: String): ExerciseFragment2 {
            val fragment = ExerciseFragment2()
            val args = Bundle()
            args.putInt(ARG_EXERCISE_IMAGE, exerciseImage)
            args.putString(ARG_EXERCISE_NAME, exerciseName)
            fragment.arguments = args
            return fragment
        }
    }
}
