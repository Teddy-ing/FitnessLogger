package com.example.fitnesslogger.ui.exercise_phase_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.ActivityExerciseBinding


//class to represent the data that the user inputs within exercise fragment2
//previously used to populate the csv file
data class ExerciseSet(
    var setNumber: Int,
    var weight: Double,
    var reps: Double,
    var exerciseName: String
)

class ExerciseSetAdapter(
    private val setsList: List<ExerciseSet>
) : RecyclerView.Adapter<ExerciseSetAdapter.ExerciseSetViewHolder>() {

    inner class ExerciseSetViewHolder(val binding: ActivityExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exerciseSet: ExerciseSet) {
            binding.tvSet.text = "Set ${exerciseSet.setNumber}"
            binding.etWeight.setText(exerciseSet.weight.toString())
            binding.etReps.setText(exerciseSet.reps.toString())

            binding.etWeight.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    exerciseSet.weight = binding.etWeight.text.toString().toDoubleOrNull() ?: 0.0
                }
            }

            binding.etReps.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    exerciseSet.reps = binding.etReps.text.toString().toDoubleOrNull() ?: 0.0
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseSetViewHolder {
        val binding = ActivityExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseSetViewHolder, position: Int) {
        holder.bind(setsList[position])
    }

    override fun getItemCount() = setsList.size
}
