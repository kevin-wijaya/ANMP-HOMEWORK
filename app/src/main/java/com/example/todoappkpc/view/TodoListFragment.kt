package com.example.todoappkpc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappkpc.R
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.viewmodel.ListTodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoListFragment : Fragment() {

    private lateinit var viewModel: ListTodoViewModel
    private val todoListAdapter  = TodoListAdapter(arrayListOf(),
        {todo ->
            viewModel.taskDone(todo.uuid)
            Toast.makeText(context, "task is done", Toast.LENGTH_SHORT).show()
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        var recViewTodo = view.findViewById<RecyclerView>(R.id.recViewTodo)
        recViewTodo.layoutManager = LinearLayoutManager(context)
        recViewTodo.adapter = todoListAdapter

        var fabAddTodo = view.findViewById<FloatingActionButton>(R.id.fabAddTodo)
        fabAddTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()

    }

    private fun observeViewModel() {
                viewModel.todoLD.observe(viewLifecycleOwner, Observer {

                    todoListAdapter.updateTodoList(it as ArrayList<Todo>)

                    var textEmpty = view?.findViewById<TextView>(R.id.textEmpty)
                    if(it.isEmpty()) {
                        textEmpty?.visibility = View.VISIBLE
                    } else {
                textEmpty?.visibility = View.GONE
            }
        })

    }

}