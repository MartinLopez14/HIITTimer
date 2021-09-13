package com.example.hiittimer.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hiittimer.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addExerciseFragment : Fragment(), NewExerciseAdapter.OnNewExerciseListener {

    private val viewModel: WorkoutViewModel by activityViewModels() {
        WorkoutViewModel.WorkoutViewModelFactory((activity?.application as HIITTimerApplication).repository)
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_exercise, container, false)
        val newExerciseAdapter = NewExerciseAdapter(viewModel.exercises.value!!, this)
        val recyclerView: RecyclerView = view.findViewById(R.id.exercisesRecycler)
        recyclerView.adapter = newExerciseAdapter

        //TODO Needs to create a workout and insert it to get the workoutID, that wa yeach exercsie can be made with the workoutID attached
        //TODO need to find out how to delete empty workout if user doesnt end up saving it as we have to insert it before they confirm they want to keep it.
        viewModel.exercises.observe(viewLifecycleOwner, Observer { newExercises ->
            newExerciseAdapter.setData(newExercises)
        })



        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveWorkout()
        var workoutId: Long = 0
        workoutId = viewModel.newWorkoutId

        val secondsText = view.findViewById<EditText>(R.id.editSeconds)
        val minutesText = view.findViewById<EditText>(R.id.editMinutes)
        val nameText = view.findViewById<EditText>(R.id.editExerciseName)

        // Add Button function
        view.findViewById<Button>(R.id.addButton)?.setOnClickListener {
            var seconds = 0
            if (secondsText.text.toString() != "" && minutesText.text.toString() != "") {
                seconds = Integer.parseInt(minutesText.text.toString()) * 60 + Integer.parseInt(secondsText.text.toString())
            } else if (minutesText.text.toString() != "") {
                seconds = Integer.parseInt(minutesText.text.toString()) * 60
            } else if (secondsText.text.toString() != "") {
                seconds = Integer.parseInt(secondsText.text.toString())
            }

            if (seconds != 0 && nameText.text.toString() != "") {
                val exerciseName = nameText.text.toString()
                workoutId = viewModel.newWorkoutId
                val exercise = Exercise(exerciseName, seconds, workoutId)
                viewModel.addExercise(exercise)
            }
        }
        // binds action to go to createWorkout Fragment

        view.findViewById<Button>(R.id.saveButton)?.setOnClickListener {
            viewModel.addAllExercises()
            viewModel.resetNewWorkout()
            view.findNavController().navigate(R.id.action_addExerciseFragment_to_homeFragment)
        }

    }


    fun saveWorkout(): Long {
        var saveName = viewModel.getNewWorkoutName()
        var saveDescription = viewModel.getNewWorkoutDesc()
        var workout = Workout(saveName, saveDescription)
        viewModel.addWorkout(workout)
        return viewModel.newWorkoutId
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onExerciseClick(position: Int) {
        TODO("Not yet implemented")
    }
}