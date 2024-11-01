package com.example.fitnesslogger.ui.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.ActivityExerciseChoice2Binding
//extremely similar to exerciseChoiceAdapter
class ExerciseChoiceAdapter2(//constructor
    private val selectedExercises: List<Pair<Int, String>>,
    private val itemClickListener: (Pair<Int, String>) -> Unit
) : RecyclerView.Adapter<ExerciseChoiceAdapter2.ExerciseViewHolder>() {


    inner class ExerciseViewHolder(val binding: ActivityExerciseChoice2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Pair<Int, String>) {
            binding.ibExercise2.setImageResource(exercise.first)
            binding.tvExerciseName2.text = exercise.second
            binding.ibExercise2.setOnClickListener {
                itemClickListener(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ActivityExerciseChoice2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(selectedExercises[position])
    }

    override fun getItemCount() = selectedExercises.size
}
