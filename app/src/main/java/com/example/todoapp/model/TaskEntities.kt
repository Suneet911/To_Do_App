package com.example.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoList")
data class TaskEntities(
    val todo: String
){
    @PrimaryKey(autoGenerate = true)
    var id=0
}
