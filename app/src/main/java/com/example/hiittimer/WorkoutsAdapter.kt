package com.example.hiittimer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutsAdapter(private var workouts: List<Workout>, private val onWorkoutListener: OnWorkoutListener)
    : RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textView: TextView
        init {
            textView = itemView.findViewById(R.id.workout_text)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onWorkoutListener.onWorkoutClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_item, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: WorkoutViewHolder, position: Int) {
        viewHolder.textView.text = workouts[position].toString()
    }

    override fun getItemCount() = workouts.size

    fun setData(newWorkouts: List<Workout>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }

    interface OnWorkoutListener {
        fun onWorkoutClick(position: Int)
    }

}
