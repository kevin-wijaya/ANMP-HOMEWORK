package com.example.todoappkpc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.model.TodoDatabase
import com.example.todoappkpc.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<Todo>()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun addTodo(todo:Todo) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    fun fetch(uuid: Int) {
        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }

    fun update(title:String, notes:String, priority:Int, uuid:Int) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().updateTodo(title, notes, priority, uuid)
        }
    }

}
