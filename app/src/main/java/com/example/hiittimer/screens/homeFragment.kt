package com.example.hiittimer.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.hiittimer.*
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [homeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class homeFragment : Fragment(), WorkoutsAdapter.OnWorkoutListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workoutsAdapter = WorkoutsAdapter(listOf(),this)
        viewModel.workouts.observe(viewLifecycleOwner, { newWorkouts ->
            workoutsAdapter.setData(newWorkouts)
        })

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = workoutsAdapter

        // binds action to go to createWorkout Fragment
        view.findViewById<FloatingActionButton>(R.id.createWorkout)?.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_addWorkoutFragment, null)
        )

    }

    override fun onWorkoutClick(position: Int) {
        viewModel.currentWorkout = viewModel.workouts.value!![position]

        viewModel.getWorkoutExercises(viewModel.currentWorkout!!.workoutId)

        view?.findNavController()?.navigate(R.id.action_homeFragment_to_workoutFragment)
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment homeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            homeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}