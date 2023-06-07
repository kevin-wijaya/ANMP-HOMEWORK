package com.example.todoappkpc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoappkpc.R
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.viewmodel.DetailTodoViewModel
import com.example.todoappkpc.viewmodel.ListTodoViewModel

class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel
    private var uuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textJudulTodo = view.findViewById<TextView>(R.id.textNewTodo)
        textJudulTodo.text = "Edit Todo"

        val buttonAdd = view.findViewById<Button>(R.id.buttonAdd)
        buttonAdd.text = "Save"

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

        observeViewModel()

        buttonAdd?.setOnClickListener{
            var txtTitle = view?.findViewById<TextView>(R.id.textTitle)
            var txtNotes = view?.findViewById<TextView>(R.id.textNotes)
            var radio = view.findViewById<RadioButton>(view.findViewById<RadioGroup>(R.id.radioGroupPriority).checkedRadioButtonId)
            viewModel.update(
                txtTitle?.text.toString(),
                txtNotes?.text.toString(),
                radio?.tag.toString().toInt(),
                uuid)
            Toast.makeText(context, "Todo Updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

    private fun observeViewModel() {
        var txtTitle = view?.findViewById<TextView>(R.id.textTitle)
        var txtNotes = view?.findViewById<TextView>(R.id.textNotes)
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            txtTitle?.setText(it.title)
            txtNotes?.setText(it.notes)

            val high = view?.findViewById<RadioButton>(R.id.radioHigh)
            val med = view?.findViewById<RadioButton>(R.id.radioMedium)
            val low = view?.findViewById<RadioButton>(R.id.radioLow)
            when(it.priority) {
                1 -> low?.isChecked = true
                2 -> med?.isChecked = true
                3 -> high?.isChecked = true
            }
        })
    }
}