package com.example.mobile_development_lab_08.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.mobile_development_lab_08.model.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): MutableList<Task>

    @Delete
    suspend fun delete(task: Task)
}
