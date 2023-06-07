package com.example.todoappkpc.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo1:Todo) //vararg dapat menerima banyak objek data tod0 misal todo1, todo2, todo3

    @Query("SELECT * FROM todo")
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid = :id") //: untuk akses parameter
    fun selectTodo(id:Int): Todo

    @Delete
    fun deleteTodo(todo:Todo)

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid=:uuid")
    fun updateTodo(title:String, notes:String, priority:Int, uuid:Int)
//    @Update
//    fun updateTodo(vararg todos: Todos) {}

    @Query("SELECT * FROM todo WHERE is_done=0 ORDER BY priority DESC")
    fun selectAllTodoDesc(): List<Todo>

    @Query("UPDATE todo SET is_done=1 WHERE uuid=:uuid")
    fun updateIsDone(uuid:Int)
}