package com.example.fitnesslogger.ui.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.ActivityChosenExerciseBinding

//extremely similar to exerciseChoiceAdapter
class SelectedExerciseAdapter(//constructor
    private val selectedExercises: MutableList<ExerciseSummary>,
    private val listener : OnItemClickListener
) : RecyclerView.Adapter<SelectedExerciseAdapter.ExerciseViewHolder>() {


    inner class ExerciseViewHolder(val binding: ActivityChosenExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(selectedExercise: ExerciseSummary) {
            binding.ibExercise2.setImageResource(selectedExercise.exerciseImage)
            binding.tvExerciseName2.text = selectedExercise.exerciseName
            binding.tvSetsDone.text = "Sets Done: ${selectedExercise.maxSetCount}"
            binding.ibExercise2.setOnClickListener {
                listener.selectedExerciseOnItemClick(selectedExercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ActivityChosenExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(selectedExercises[position])
    }

    override fun getItemCount() = selectedExercises.size

    interface OnItemClickListener {
        fun selectedExerciseOnItemClick(selectedExercise: ExerciseSummary)
    }

}
