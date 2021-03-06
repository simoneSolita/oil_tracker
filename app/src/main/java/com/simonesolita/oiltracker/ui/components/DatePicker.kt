package com.simonesolita.oiltracker.ui.components

import android.app.DatePickerDialog
import android.content.Context
import com.simonesolita.oiltracker.utils.getDate
import com.simonesolita.oiltracker.utils.toCalendar
import java.util.*

fun createDatePicker(
    context : Context,
    startDate : Date,
    onValueChange: (Date) -> Unit,
    minDate : Date? = null,
    maxDate : Date? = null
)
{
    val time = startDate.toCalendar()
    val datePickerDialog = DatePickerDialog(
        context, { _, year: Int, month: Int, dayOfMonth: Int ->
            onValueChange(getDate(dayOfMonth, month, year))
        }, time.year, time.month, time.day
    )
    minDate?.time?.let { datePickerDialog.datePicker.minDate = it }
    maxDate?.time?.let { datePickerDialog.datePicker.maxDate = it }

    datePickerDialog.show()
}