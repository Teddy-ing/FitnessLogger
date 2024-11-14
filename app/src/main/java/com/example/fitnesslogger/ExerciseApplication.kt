package com.example.fitnesslogger

import android.app.Application
import com.example.fitnesslogger.data.db.ExerciseDatabase
import com.example.fitnesslogger.data.repositories.ExerciseRepository
import com.example.fitnesslogger.data.repositories.ExerciseSetRepository
import com.example.fitnesslogger.ui.calendar.CalendarViewModelFactory
import com.example.fitnesslogger.ui.exercise.ExerciseViewModelFactory
import com.example.fitnesslogger.ui.exercise_phase_2.ExerciseDialogViewModelFactory
import org.kodein.di.DI
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

class ExerciseApplication : Application() {

    //dependency injection container
    val di = DI.lazy {
        import(androidXModule(this@ExerciseApplication))

        //database
        bind<ExerciseDatabase>() with singleton {ExerciseDatabase(instance())}

        //Repositories
        bind<ExerciseRepository>() with singleton { ExerciseRepository(instance()) }
        bind<ExerciseSetRepository>() with singleton {ExerciseSetRepository(instance())}

        //ViewModel Factories
        bind<CalendarViewModelFactory>() with provider {CalendarViewModelFactory(instance(), instance())}
        bind<ExerciseViewModelFactory>() with provider {ExerciseViewModelFactory(instance(), instance())}
        bind<ExerciseDialogViewModelFactory>() with provider {ExerciseDialogViewModelFactory(instance(),instance())}
    }

}