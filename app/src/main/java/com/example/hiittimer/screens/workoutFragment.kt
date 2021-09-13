package com.example.hiittimer.screens

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hiittimer.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [workoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class workoutFragment : Fragment() {

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout, container, false)
        val exerciseAdapter = ExerciseAdapter(listOf())

        viewModel.currentExercises?.observe(viewLifecycleOwner, { newExercises ->
            exerciseAdapter.setData(newExercises)
        })

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = exerciseAdapter

        view.findViewById<TextView>(R.id.workoutName).text = viewModel.currentWorkout!!.name
        view.findViewById<TextView>(R.id.timer).text = viewModel.currentWorkout!!.description

        view.findViewById<Button>(R.id.playPauseButton)?.setOnClickListener{

            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        viewModel.deleteWorkout()
                        view.findNavController().navigate(R.id.action_workoutFragment_to_homeFragment)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
            val alert = builder.create()
            alert.show()

        }

        view.findViewById<Button>(R.id.stopButton)?.setOnClickListener{
            view.findNavController().navigate(R.id.action_workoutFragment_to_playWorkoutFragment)
        }

        view.findViewById<Button>(R.id.shareButton)?.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:")
            val mesg = "I am about to do my workout - " + viewModel.currentWorkout!!.name + ". Come join me!"
            sendIntent.putExtra("sms_body", mesg)
            startActivity(sendIntent)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment workoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            workoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}