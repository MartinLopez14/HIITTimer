package com.example.hiittimer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private var exercises: List<Exercise>)
    : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        val exerciseText: TextView
        val exerciseTime : TextView
        init {
            exerciseText = itemView.findViewById(R.id.exercise_text)
            exerciseTime = itemView.findViewById(R.id.exercise_time)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view )
    }

    override fun onBindViewHolder(viewHolder: ExerciseViewHolder, position: Int) {
        viewHolder.exerciseText.text = exercises[position].toString()
        viewHolder.exerciseTime.text = exercises[position].secondsToString()
    }

    override fun getItemCount() = exercises.size

    fun setData(newExercises: List<Exercise>) {
        exercises = newExercises
        for (exer in newExercises) {
        }
        notifyDataSetChanged()
    }

    interface OnNewExerciseListener {
        fun onExerciseClick(position: Int)
    }


}