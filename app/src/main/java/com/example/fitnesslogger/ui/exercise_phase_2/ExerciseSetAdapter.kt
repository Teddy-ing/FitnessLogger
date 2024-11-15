package com.example.fitnesslogger.ui.exercise_phase_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.databinding.ActivityExerciseBinding


//class to represent the data that the user inputs within exercise fragment2
//previously used to populate the csv file


class ExerciseSetAdapter(
    //items being a list of all exercise Sets of the same exerciseID
    private val items: List<ExerciseSet>,
    private val viewModel : ExerciseDialogViewModel

) : RecyclerView.Adapter<ExerciseSetAdapter.ExerciseSetViewHolder>() {

    inner class ExerciseSetViewHolder(val binding: ActivityExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseSet) {
            binding.tvSet.text = "Set ${item.setCount}"
            binding.etWeight.setText(item.weight.toString())
            binding.etReps.setText(item.reps.toString())

            binding.etWeight.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    // Clear the default value 0.0 when the user clicks into the EditText
                    if (binding.etWeight.text.toString() == "0.0") {
                        binding.etWeight.setText("")
                    }
                } else {
                    // Save the value when the user leaves the EditText

                    item.weight = binding.etWeight.text.toString().toDoubleOrNull() ?: 0.0
                    viewModel.upsertSet(item)
                }
            }

            binding.etReps.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    // Clear the default value 0.0 when the user clicks into the EditText
                    if (binding.etReps.text.toString() == "0.0") {
                        binding.etReps.setText("")
                    }
                } else {
                    // Save the value when the user leaves the EditText

                    item.reps = binding.etReps.text.toString().toDoubleOrNull() ?: 0.0
                    viewModel.upsertSet(item)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseSetViewHolder {
        val binding = ActivityExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseSetViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
