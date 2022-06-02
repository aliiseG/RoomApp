package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.TaskDao
import com.example.roomapp.model.Task

class TaskRepository(private val taskDao: TaskDao) {
//Nolasa visus entities no database
    val readAllDataASC: LiveData<List<Task>> = taskDao.readAllDataASC()

    //pievienot objektu
    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }
    //atjauninat objekta vertibas
    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
    //izdzest vienu objektu
    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
    //izdzest visus objektus no tabulas
    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }

}