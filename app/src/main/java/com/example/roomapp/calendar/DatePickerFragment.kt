package com.example.roomapp.calendar

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment:  DialogFragment(), DatePickerDialog.OnDateSetListener{
    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //lai atver uz sodienas datumu
        val yr = calendar.get(Calendar.YEAR)
        val mm = calendar.get(Calendar.MONTH)
        val dd = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(), this, yr, mm, dd)
    }

    override fun onDateSet(view: DatePicker?, yr: Int,mm: Int, dd: Int) {
        calendar.set(Calendar.YEAR, yr)
        calendar.set(Calendar.MONTH, mm)
        calendar.set(Calendar.DAY_OF_MONTH, dd)
        val selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)

        val selectedDateBundle = Bundle()
        selectedDateBundle.putString("SELECTED_DATE", selectedDate)

        setFragmentResult("REQUEST_KEY", selectedDateBundle)

    }

}