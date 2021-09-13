package com.example.hiittimer

import android.app.Application

class HIITTimerApplication : Application() {
    val database by lazy { WorkoutDatabase.getDatabase(this) }
    val repository by lazy { WorkoutRepository(database.workoutDao(), database.exerciseDao()) }
}
