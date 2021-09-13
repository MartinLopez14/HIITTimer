package com.example.hiittimer

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithExercise(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "parentWorkoutId"
    )
    val exercises: List<Exercise>
)
