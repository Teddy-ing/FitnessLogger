package com.example.fitnesslogger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.ActivityExerciseChoiceBinding

class ExerciseChoiceAdapter(//constructor
    private val exerciseList: List<Pair<Int, String>>,
    private val itemClickListener: (Pair<Int, String>) -> Unit
) : RecyclerView.Adapter<ExerciseChoiceAdapter.ExerciseChoiceViewHolder>() {

    //binds the image and name to the respective views and sets an onclickListener
    inner class ExerciseChoiceViewHolder(val binding: ActivityExerciseChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Pair<Int, String>) {
            binding.ibExercise.setImageResource(exercise.first)
            binding.tvExerciseName.text = exercise.second
            binding.ibExercise.setOnClickListener {
                itemClickListener(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseChoiceViewHolder {
        val binding = ActivityExerciseChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseChoiceViewHolder, position: Int) {
        holder.bind(exerciseList[position])
    }

    override fun getItemCount() = exerciseList.size
}