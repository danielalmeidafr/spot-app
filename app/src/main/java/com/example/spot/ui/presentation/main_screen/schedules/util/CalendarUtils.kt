package com.example.spot.ui.presentation.main_screen.schedules.util

import java.time.LocalDate
import java.time.YearMonth

data class CalendarDay(val date: LocalDate, val inMonth: Boolean)

fun generateCalendarDays(year: Int, month: Int): List<CalendarDay> {
    val ym = YearMonth.of(year, month)
    val firstOfMonth = ym.atDay(1)
    val startOffset = firstOfMonth.dayOfWeek.value % 7
    val prevYm = ym.minusMonths(1)
    val prevLength = prevYm.lengthOfMonth()
    val days = mutableListOf<CalendarDay>()

    for (i in 0 until startOffset) {
        val day = prevYm.atDay(prevLength - startOffset + 1 + i)
        days.add(CalendarDay(day, false))
    }

    for (d in 1..ym.lengthOfMonth()) {
        days.add(CalendarDay(ym.atDay(d), true))
    }

    val nextYm = ym.plusMonths(1)
    var nextDay = 1
    while (days.size < 42) days.add(CalendarDay(nextYm.atDay(nextDay++), false))

    return days
}
