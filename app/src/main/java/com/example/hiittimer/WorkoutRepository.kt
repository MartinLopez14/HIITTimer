package com.example.hiittimer

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao, private val exerciseDao: ExerciseDao) {
    val workouts: Flow<List<Workout>> = workoutDao.getWorkouts()

    val numWorkouts: Flow<Int> = workoutDao.getCount()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(exercises: List<Exercise>): List<Long> {
        return exerciseDao.insertAll(exercises)
    }

    fun getExercisesOfWorkout(workoutId: Long): Flow<List<Exercise>> {
        return workoutDao.getExercisesOfWorkout(workoutId)
    }

    suspend fun deleteWorkout(workoutId: Long) {
        workoutDao.deleteWorkout(workoutId)
        exerciseDao.deleteWorkoutExercises(workoutId)
    }
}
