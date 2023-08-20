package com.example.todoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.TaskEntities
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val taskRepository: TaskRepository): ViewModel() {

    fun getTask(): LiveData<List<TaskEntities>>{
        return taskRepository.getTask()
    }

    fun addTask(taskEntities: TaskEntities){
        viewModelScope.launch ( Dispatchers.IO){
            taskRepository.addTask(taskEntities)
        }

    }
    fun editTask(taskEntities: TaskEntities){
        viewModelScope.launch(Dispatchers.IO){
            taskRepository.editTask(taskEntities)
        }
    }
    fun deleteTask(taskEntities: TaskEntities){
        viewModelScope.launch(Dispatchers.IO){
            taskRepository.deleteTask(taskEntities)
        }
    }
}