package com.example.todoappkpc.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoappkpc.R
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.viewmodel.DetailTodoViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java) //initial view model

        val btnAdd = view.findViewById<Button>(R.id.buttonAdd)
        btnAdd.setOnClickListener {
            val txtTitle = view.findViewById<EditText>(R.id.textTitle)
            val txtNotes = view.findViewById<EditText>(R.id.textNotes)
            val radio = view.findViewById<RadioButton>(
                view.findViewById<RadioGroup>(R.id.radioGroupPriority).checkedRadioButtonId
            )
            val todo = Todo(
                txtTitle.text.toString(),
                txtNotes.text.toString(),
                radio.tag.toString().toInt(),
            0)

            viewModel.addTodo(todo)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()

        }
    }

}
