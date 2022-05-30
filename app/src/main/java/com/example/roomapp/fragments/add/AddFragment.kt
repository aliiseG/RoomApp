package com.example.roomapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.Task
import com.example.roomapp.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val taskName = addtask_name_et.text.toString()
        val deadline = adddeadline_et.text.toString()
        //https://syntaxfix.com/question/737/how-to-get-current-local-date-and-time-in-kotlin
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        if(inputCheck(taskName, deadline, currentDate)){
            val task = Task(
                0,
                taskName,
                deadline,
                currentDate
            )

            mTaskViewModel.addTask(task)
            Toast.makeText(requireContext(), "Task successfully added!", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Fields left empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(task_name: String, deadline: String, dateCreated: String): Boolean{
        return !(TextUtils.isEmpty(task_name) && TextUtils.isEmpty(deadline) && TextUtils.isEmpty(dateCreated))
    }

}