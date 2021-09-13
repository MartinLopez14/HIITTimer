package com.example.hiittimer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewExerciseAdapter(private var exercises: List<Exercise>, private val onNewExerciseListener: OnNewExerciseListener)
    : RecyclerView.Adapter<NewExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View, private val onNewExerciseListener: OnNewExerciseListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val exerciseText: TextView
        val exerciseTime : TextView
        init {
            exerciseText = itemView.findViewById(R.id.exercise_text)
            exerciseTime = itemView.findViewById(R.id.exercise_time)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onNewExerciseListener.onExerciseClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view, onNewExerciseListener)
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