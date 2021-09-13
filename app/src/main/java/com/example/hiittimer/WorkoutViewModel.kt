package com.example.hiittimer

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WorkoutViewModel(private val  workoutRepository: WorkoutRepository) : ViewModel() {

    val workouts: LiveData<List<Workout>> = workoutRepository.workouts.asLiveData()

    private var newWorkoutName = ""
    private var newWorkoutDesc = ""
    var newWorkoutId: Long = 0

    private var _exercises = MutableLiveData<MutableList<Exercise>>(arrayListOf<Exercise>())


    fun addWorkout(workout: Workout) {
        var newId : Long = 999
        viewModelScope.launch {
            newId = workoutRepository.insert(workout)
            newWorkoutId = newId
        }
    }

    fun addAllExercises() = viewModelScope.launch {
        workoutRepository.insertAll(_exercises.value!!)
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun setNewWorkoutName(name: String) {
        newWorkoutName = name
    }

    fun getNewWorkoutName(): String {
        return newWorkoutName
    }

    fun setNewWorkoutDesc(desc: String) {
        newWorkoutDesc = desc
    }

    fun getNewWorkoutDesc(): String {
        return newWorkoutDesc
    }

    val exercises: LiveData<MutableList<Exercise>>
        get() = _exercises

    private var _numExercises = MutableLiveData<Int>(exercises.value!!.size)
    val numExercises: LiveData<Int>
        get() = _numExercises

    fun addExercise(exercise: Exercise) {
        _exercises.value?.add(exercise)
        _exercises.notifyObserver()
        _numExercises.value = _exercises.value!!.size
    }

    fun resetNewWorkout() {
        newWorkoutName = ""
        newWorkoutDesc = ""
        _exercises = MutableLiveData<MutableList<Exercise>>(arrayListOf())
    }

    var currentWorkout: Workout? = null
    var currentExercises: LiveData<List<Exercise>>? = null

    fun getWorkoutExercises(workoutId: Long)  = viewModelScope.launch {
       currentExercises = workoutRepository.getExercisesOfWorkout(workoutId).asLiveData()
    }

    fun deleteWorkout(){
        viewModelScope.launch {
            workoutRepository.deleteWorkout(currentWorkout!!.workoutId)
        }
    }


    class WorkoutViewModelFactory(private val repository: WorkoutRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WorkoutViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}