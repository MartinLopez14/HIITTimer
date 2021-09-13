package com.example.hiittimer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercises")
data class Exercise(@ColumnInfo var name: String,
               @ColumnInfo var seconds: Int,
               @ColumnInfo var parentWorkoutId: Long) {

    @PrimaryKey(autoGenerate = true) var exerciseId: Long = 0

    override fun toString(): String {
        return name
    }

    fun secondsToString(): String {
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