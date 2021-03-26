package krasnikov.project.pmfightacademy.utils

import com.prolificinteractive.materialcalendarview.CalendarDay

private const val FORMAT_DAY_AND_MONTH = "%02d"

fun CalendarDay.getFormattedDate(): String {
    return "${FORMAT_DAY_AND_MONTH.format(month)}/${FORMAT_DAY_AND_MONTH.format(day)}/$year"
}
