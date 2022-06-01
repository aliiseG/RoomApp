package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomapp.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM tasks_table ORDER BY id ASC")
    fun readAllDataASC(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks_table ORDER BY id DESC")
    fun readAllDataDESC(): LiveData<List<Task>>

}