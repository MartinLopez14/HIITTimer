package com.example.hiittimer

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<Exercise>): List<Long>

    @Update
    suspend fun update(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<Exercise>>

    @Query("SELECT COUNT(*) FROM exercises")
    fun getCount(): Flow<Int>

    @Query("DELETE FROM exercises WHERE parentWorkoutId = :parentWorkoutId")
    suspend fun deleteWorkoutExercises(parentWorkoutId: Long)
}