package com.example.fitnesslogger.ui.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.ActivityExerciseChoiceBinding

class ExerciseChoiceAdapter(//constructor
    private val exerciseList: List<Pair<Int, String>>,
    private val listener: OnItemClickListener,
    private val selectedGroup : String
) : RecyclerView.Adapter<ExerciseChoiceAdapter.ExerciseChoiceViewHolder>() {


    inner class ExerciseChoiceViewHolder(val binding: ActivityExerciseChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //creates a fun for onBindViewHolder that bind the respective exercise chosen from the
            // list
        fun bind(exercise: Pair<Int, String>) {
            binding.ibExercise.setImageResource(exercise.first)
            binding.tvExerciseName.text = exercise.second
            binding.ibExercise.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition, exercise, selectedGroup)
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

    interface OnItemClickListener {
        fun onItemClick(position: Int, exercise : Pair<Int, String>, selectedGroup : String)
    }

}