package com.example.roomapp.fragments.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
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
    // kalendaram
    var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate fragment layout
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // view model
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // date picker dialog
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // onclicklistener atver kalendara dialog, kad uzspiez uz podzinas
        view.button_date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(requireContext(),
                    dateSetListener,
                    // lai raditu sodienas datumu, kad atver kalendaru
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }
    //aizsuta tekstu uz laucinu aka EditText ar "due.."
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        adddeadline_et!!.setText(sdf.format(cal.getTime()))
    }
    //nolasa ievadito tekstu un ievieto ka entity datubazee
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


