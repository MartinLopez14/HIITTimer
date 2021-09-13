package com.example.hiittimer

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insert(workout: Workout): Long

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

//    @Query("SELECT * FROM workouts")
//    fun getAll(): Flow<List<Workout>>

    @Query("SELECT COUNT(*) FROM workouts")
    fun getCount(): Flow<Int>


    @Query("SELECT * FROM workouts")
    fun getWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM exercises WHERE parentWorkoutId = :workoutId")
    fun getExercisesOfWorkout(workoutId: Long) : Flow<List<Exercise>>

    @Query ("DELETE FROM workouts WHERE workoutId = :workoutId")
    suspend fun deleteWorkout(workoutId: Long)




}