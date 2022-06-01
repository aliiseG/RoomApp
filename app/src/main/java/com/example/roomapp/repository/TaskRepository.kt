package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.TaskDao
import com.example.roomapp.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val readAllDataASC: LiveData<List<Task>> = taskDao.readAllDataASC()

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }

}