package com.example.fitnesslogger.ui.exercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesslogger.data.db.entities.Exercise
import com.example.fitnesslogger.data.db.entities.ExerciseSet
import com.example.fitnesslogger.data.db.results.ExerciseSetWithGroup
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {

    fun getAllExerciseSetsWithGroupByDate(date: String) = exerciseSetRepository.getAllExerciseSetsWithGroupByDate(date)

    //this day monthAndYear and month
    private var _args = MutableLiveData(mutableMapOf<String, String>())
    //the varible that frag observes
    val args: LiveData<MutableMap<String, String>> = _args


    fun getArguments(day :String, monthAndYear : String, month : String) {
        val argumentMap = mutableMapOf<String, String>(
            "day" to day,
            "monthAndYear" to monthAndYear,
            "month" to month
        )

        _args.value = argumentMap
    }

    //function that gets a list of all exerciseSet entities associated with a specific day
    //
    fun getCurrentExercises(items : List<ExerciseSetWithGroup> ) : MutableList<ExerciseFragment.ExerciseSummary> {
        val exerciseSummaries = mutableListOf<ExerciseFragment.ExerciseSummary>()

        val biggestSets = mutableMapOf<Int, Int>() // exerciseId -> set count
        items.forEach { item ->
            val exerciseId = item.exerciseSet.exerciseId
            //gets the current eID

            biggestSets[exerciseId] = (biggestSets[exerciseId] ?: 0) + 1
            //if the current Id doesnt exist in the map, assigns it to the next avaiable index. then for each loop iteration increments set val

            //if its a new eID
            if (biggestSets[exerciseId] == 1) {
                exerciseSummaries.add(

                    ExerciseFragment.ExerciseSummary(
                        exerciseId = exerciseId,
                        exerciseName = item.name,
                        exerciseGroup = item.group,
                        maxSetCount = 1
                    )
                )
            } else {
                // increments the current exerciseId group's set count by 1
                exerciseSummaries.find { it.exerciseId == exerciseId }?.maxSetCount = biggestSets[exerciseId]!!

            }
        }

        return exerciseSummaries
    }

    //on click functionality when user chooses an exercise to add from the avaiable list of exercises



    ///////////////Database Functions///////////////////

    //upsert function to add to the exercise table
    fun upsertExercise(item: Exercise): Int {
        val a = 0
        CoroutineScope(Dispatchers.Main).launch {
             exerciseRepository.upsert(item)
        }
        return a
    }

    //upsert function that upserts the exercise first then upserts the exerciseSet with its ExerciseID

    fun upsertExerciseAndExerciseSet(exercise : Pair<Int, String>, selectedGroup : String) {
        Log.d("taggy", exercise.toString())
        Log.d("taggy", exercise.first.toString())
        Log.d("taggy", exercise.second)
        Log.d("taggy", selectedGroup)
        Log.d("taggy", "")


                                    //id  exercise Name    exercise group  image value'
        val exerciseItem = Exercise(0, exercise.second, selectedGroup, exercise.first)

        Log.d("taggy", exerciseItem.toString())


        //asynchronousely upserts multiple items
        viewModelScope.launch {
            val exerciseId = exerciseRepository.upsert(exerciseItem)
            //now adds exercise Set using the newly created exerciseID which references the exercise
            Log.d("taggy", exerciseId.toString())

            val exerciseSetItem = ExerciseSet(0, exerciseId, _args.value!!["day"] +_args.value!!["monthAndYear"], 1, 0.0, 0.0)
            Log.d("taggy", exerciseSetItem.toString())
            exerciseSetRepository.upsert(exerciseSetItem)
        }

    }


    fun upsertExerciseSet(item: ExerciseSet) = CoroutineScope(Dispatchers.Main).launch {
        exerciseSetRepository.upsert(item)
    }

    fun getMaxId(): Int = exerciseRepository.getMaxId()


    fun getAllExercises() = exerciseRepository.getALlExercises()

    fun getAllExerciseSets() = exerciseSetRepository.getALlExerciseSets()

}