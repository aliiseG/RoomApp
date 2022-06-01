package com.example.roomapp.fragments.update

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Task
import com.example.roomapp.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        view.updatetask_name_et.setText(args.currentTask.task_name)
        view.updatedeadline_et.setText(args.currentTask.deadline)
        view.updatedateCreated_et.setText(args.currentTask.dateCreated)

        view.update_btn.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val task_name = updatetask_name_et.text.toString()
        val deadline = updatedeadline_et.text.toString()
        val dateCreated = updatedateCreated_et.text.toString()

        if (inputCheck(task_name, deadline, dateCreated)) {
            // Create Task Object
            val updatedTask = Task(args.currentTask.id, task_name, deadline, dateCreated)
            // Update Current Task
            mTaskViewModel.updateTask(updatedTask)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(task_name: String, deadline: String, dateCreated: String): Boolean {
        return !(TextUtils.isEmpty(task_name) && TextUtils.isEmpty(deadline) && TextUtils.isEmpty(dateCreated))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            (R.id.menu_delete) -> deleteTask()
            (R.id.menu_share_task) -> {
                shareTask()
            }
            (R.id.menu_task_reminder) -> {
                val epoch = fromStringToEpoch(args.currentTask.deadline)
                addEvent(args.currentTask.task_name, epoch, true)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun fromStringToEpoch(deadline: String):Long{
        //https://stackoverflow.com/questions/46892944/convert-string-to-epoch-time
        val changeSymbol = deadline.replace("/", "-")
        val formattedDate = changeSymbol + " 00:00:00.000"
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS")
        val date: Date = dateFormat.parse(formattedDate)
        val epoch: Long = date.getTime()
        return epoch
    }

    private fun shareTask(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "Task name: ${args.currentTask.task_name}\n" +
                        "Task deadline: ${args.currentTask.deadline}\n" +
                        "Task created on: ${args.currentTask.dateCreated}"
            )
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    //https://developer.android.com/guide/components/intents-common
    private fun addEvent(title: String, begin: Long, allDay:Boolean) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin+86400)
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, allDay)
        }
        val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
    }
    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteTask(args.currentTask)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentTask.task_name}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentTask.task_name}?")
        builder.setMessage("Are you sure you want to delete ${args.currentTask.task_name}?")
        builder.create().show()
    }
}