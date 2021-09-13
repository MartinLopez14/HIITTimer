package com.example.hiittimer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @ColumnInfo var name: String = "",
    @ColumnInfo var description: String = "") {

    @PrimaryKey(autoGenerate = true) var workoutId: Long = 0

    override fun toString(): String {
        return name
    }



}