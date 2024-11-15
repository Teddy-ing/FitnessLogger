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

) : RecyclerView.Adapter<ExerciseSetAdapter.ExerciseSetViewHolder>() {

    inner class ExerciseSetViewHolder(val binding: ActivityExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseSet) {
            binding.tvSet.text = "Set ${item.setCount}"
            binding.etWeight.setText(item.weight.toString())
            binding.etReps.setText(item.reps.toString())

            binding.etWeight.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {

                    //if(binding.etWeight.text.toString() == "0.0") {
                    //    binding.etWeight.setText("")
                   // }
                    item.weight = binding.etWeight.text.toString().toDoubleOrNull() ?: 0.0
                }
            }

            binding.etReps.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    item.reps = binding.etReps.text.toString().toDoubleOrNull() ?: 0.0
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
