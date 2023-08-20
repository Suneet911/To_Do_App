package com.example.todoapp.adapter

import android.app.AlertDialog
import android.content.ClipData.Item
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.R
import com.example.todoapp.model.TaskEntities

class TaskAdapter(val context: Context, val listener: ITaskUpdate) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    val taskList = ArrayList<TaskEntities>()


    inner class TaskViewHolder(itemView: View) : ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.taskList)
        val menu: ImageView = itemView.findViewById(R.id.menuButton)


        init {
            menu.setOnClickListener {
                popUpMenu(it)
            }
        }


        private fun popUpMenu(itemView: View) {

            val popupMenu = PopupMenu(context, itemView)
            popupMenu.inflate(R.menu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editBtn -> {
                        listener.editTask(taskList[bindingAdapterPosition])
                        notifyDataSetChanged()
                        true
                    }

                    R.id.deleteButton -> {
                        listener.deleteTask(taskList[bindingAdapterPosition])
                        Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> true
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
            }
            popupMenu.show()
//            val popUp = PopupMenu::class.java.getDeclaredField("menu")
//            popUp.isAccessible = true
//            val menus = popUp.get(popupMenu)
//            menus.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
//                .invoke(menus, true)
//
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.task.text = currentTask.todo
    }

    fun updateTask(newTask: List<TaskEntities>) {
        taskList.clear()
        taskList.addAll(newTask)
        notifyDataSetChanged()
    }
}

interface ITaskUpdate {
    fun editTask(taskEntities: TaskEntities)
    fun deleteTask(taskEntities: TaskEntities)
}