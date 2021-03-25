package krasnikov.project.pmfightacademy.utils

import com.prolificinteractive.materialcalendarview.CalendarDay

private const val formatDayAndMonth = "%02d"

fun CalendarDay.getFormattedDate(): String {
    return "${formatDayAndMonth.format(day)}.${formatDayAndMonth.format(month)}.$year"
}
