package com.example.fitnesslogger.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fitnesslogger.data.db.entities.Exercise
import com.example.fitnesslogger.data.db.entities.ExerciseSet

@Database(
    entities = [Exercise::class], [ExerciseSet::class],
    version = 1,
    exportSchema = false
)

abstract class ExerciseDatabase: RoomDatabase() {

    abstract fun getExerciseDao() : ExerciseDao

    abstract fun getExerciseSetDao() : ExerciseSetDao

    companion object {
        @Volatile

        private var instance: ExerciseDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK) {
            instance  ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context : Context) =
            Room.databaseBuilder(context.applicationContext,
                ExerciseDatabase::class.java, "ExerciseDB.db").build()
    }


}