package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.ITaskUpdate
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.model.TaskDataBase
import com.example.todoapp.model.TaskEntities
import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.viewModel.MainViewModel
import com.example.todoapp.viewModel.MainViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ITaskUpdate {

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TaskAdapter(this, this)
        val recyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        val dao = TaskDataBase.getDataBase(applicationContext).taskDao()
        val repository = TaskRepository(dao)

        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(repository)
        )[MainViewModel::class.java]

        mainViewModel.getTask().observe(this, Observer { list ->
            list?.let {
                adapter.updateTask(it)
            }
        })

        val btn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        btn.setOnClickListener {
            setUpPopupMenu()
        }
    }


    private fun setUpPopupMenu() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.entry_task_popup, null)
        val newTask: EditText = v.findViewById(R.id.task_editText)

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->

            val task = newTask.text.toString()
            mainViewModel.addTask(TaskEntities(task))
//            Log.d("DATA", task)
            Toast.makeText(this, "New Task Added", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()

    }

    override fun editTask(taskEntities: TaskEntities) {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.entry_task_popup, null)


        val newTask: EditText = v.findViewById(R.id.task_editText)
        newTask.setText(taskEntities.todo)

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val task = newTask.text.toString()

            mainViewModel.editTask(TaskEntities(task))

            Log.d("DATA", task)
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }

    override fun deleteTask(taskEntities: TaskEntities) {
        mainViewModel.deleteTask(taskEntities)
    }

}


