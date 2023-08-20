package com.example.todoapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    suspend fun addTask(taskEntities: TaskEntities)

    @Delete
    suspend fun deleteTask(taskEntities: TaskEntities)

    @Update
    suspend fun updateTask(taskEntities: TaskEntities)

    @Query(" Select * from todoList")
    fun getTask(): LiveData<List<TaskEntities>>
}