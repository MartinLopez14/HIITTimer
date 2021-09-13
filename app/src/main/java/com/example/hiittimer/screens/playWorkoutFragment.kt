package com.example.hiittimer.screens

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.hiittimer.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [playWorkoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class playWorkoutFragment : Fragment() {

    private val viewModel: WorkoutViewModel by activityViewModels() {
        WorkoutViewModel.WorkoutViewModelFactory((activity?.application as HIITTimerApplication).repository)
    }

    private var isPaused = true
    private var resumeFromSecs : Long = 0
    private var exerciseIndex: Int = 0


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
        val view = inflater.inflate(R.layout.fragment_play_workout, container, false)
        view.findViewById<TextView>(R.id.workoutName).text = viewModel.currentWorkout?.name

         var exercises : List<Exercise> = viewModel.currentExercises?.value!!
         val exerciseCount = viewModel.currentExercises?.value!!.count()

        val currentExerciseText = view.findViewById<TextView>(R.id.currentExercise)
        val nextExerciseText = view.findViewById<TextView>(R.id.nextExercise)

        if (exerciseCount > 0) {
            currentExerciseText.text = exercises[exerciseIndex].name
            if (exerciseCount > 1) {
                nextExerciseText.text = exercises[exerciseIndex+1].name
            }
        }

        val playPauseButton = view.findViewById<Button>(R.id.playPauseButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)
        stopButton.isEnabled = true

        resumeFromSecs = exercises[exerciseIndex].seconds.toLong()

        playPauseButton.setOnClickListener {
            if (isPaused) {
                // if paused set to unpaused and disbale stop button
                isPaused = false
                playPauseButton.text = resources.getString(R.string.pause)
                stopButton.isEnabled = false
                playPauseButton.background.setTint(resources.getColor(R.color.yellow))
                val animation = AnimationUtils.loadAnimation(activity, R.anim.fade_out)
                stopButton.startAnimation(animation)
                Handler().postDelayed({
                    stopButton.visibility = View.GONE
                }, 1000)
                Log.i("debug", resumeFromSecs.toString())
                startTimeCounter(view, resumeFromSecs).start()
            } else {
                // if unpaused then set to paused and enable stop button
                isPaused = true
                playPauseButton.text = resources.getString(R.string.play)
                playPauseButton.background.setTint(resources.getColor(R.color.green))
                stopButton.isEnabled = true
                stopButton.visibility = View.VISIBLE
                val animation = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
                stopButton.startAnimation(animation)
            }
        }

        stopButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_playWorkoutFragment_to_homeFragment)
        }

        // Inflate the layout for this fragment
        return view
    }



    fun startTimeCounter(view: View, exerciseLength: Long) : CountDownTimer {

        val currentExerciseText = view.findViewById<TextView>(R.id.currentExercise)
        val nextExerciseText = view.findViewById<TextView>(R.id.nextExercise)

         var exercises : List<Exercise> = viewModel.currentExercises?.value!!
         val exerciseCount = viewModel.currentExercises?.value!!.count()

        var counter: Long = 0
        val countTime: TextView = view.findViewById(R.id.timer)
        return object : CountDownTimer(exerciseLength*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (isPaused) {
                    resumeFromSecs = exerciseLength - counter
                    cancel()
                } else {
                    countTime.text = secondsToString(exerciseLength - counter)
                }
                counter++
            }
            override fun onFinish() {
                val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
                if (exerciseCount - exerciseIndex > 1) {
                    val progressPercentage : Float = ((exerciseIndex+1).toFloat()/exerciseCount.toFloat()).toFloat()
                    resumeFromSecs = exercises[exerciseIndex+1].seconds.toLong()
                    currentExerciseText.text = exercises[exerciseIndex+1].name
                    if (exerciseCount - exerciseIndex > 2) {
                        nextExerciseText.text = exercises[exerciseIndex+2].name
                    } else {
                        nextExerciseText.text = ""
                    }

                    exerciseIndex++
                    progressBar.progress = (progressPercentage * 100).toInt()
                    startTimeCounter(view, resumeFromSecs).start()
                } else {
                    countTime.text = "00:00"
                    progressBar.progress = 100
                    view.findViewById<Button>(R.id.playPauseButton).visibility = View.GONE
                    view.findViewById<Button>(R.id.playPauseButton).isEnabled = false

                    view.findViewById<Button>(R.id.stopButton).visibility = View.VISIBLE
                    view.findViewById<Button>(R.id.stopButton).isEnabled = true

                }




                //TODO flick to next exercise
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment playWorkoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            playWorkoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun secondsToString(seconds: Long): String {
        var mins = ""
        mins = if (seconds / 60 < 10) {
            "0" + (seconds / 60).toString()
        } else {
            (seconds / 60).toString()
        }
        var secs = ""

        secs = if (seconds % 60 < 10) {
            "0" + (seconds % 60).toString()
        } else {
            (seconds % 60).toString()
        }

        return ("$mins:$secs")
    }
}