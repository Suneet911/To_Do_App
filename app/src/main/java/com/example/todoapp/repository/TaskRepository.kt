package com.example.todoapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.model.TaskDao
import com.example.todoapp.model.TaskDataBase
import com.example.todoapp.model.TaskEntities

class TaskRepository (private val taskDao: TaskDao){

    fun getTask(): LiveData<List<TaskEntities>>{
        return taskDao.getTask()
    }

    suspend fun addTask(taskEntities: TaskEntities){
        taskDao.addTask(taskEntities)
    }
    suspend fun editTask(taskEntities: TaskEntities){
        taskDao.updateTask(taskEntities)
    }

    suspend fun deleteTask(taskEntities: TaskEntities){
        taskDao.deleteTask(taskEntities)
    }
}