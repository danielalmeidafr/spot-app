package com.example.spot.ui.presentation.schedule_service.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class ServiceCalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean
)

@Composable
fun ServiceCalendarPager(
    modifier: Modifier = Modifier,
    selectedDay: LocalDate,
    availableDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit
) {
    val pageCount = 24
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val currentMonth = remember { YearMonth.now() }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            val monthToShow = currentMonth.plusMonths(page.toLong())
            val days = remember(monthToShow) { generateDaysForMonthFixed(monthToShow) }

            Column {
                Text(
                    text = "${monthToShow.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR")).replaceFirstChar { it.uppercase() }} ${monthToShow.year}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    textAlign = TextAlign.Center
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SAB").forEach { weekDay ->
                        Text(
                            text = weekDay,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    userScrollEnabled = false
                ) {
                    items(days) { day ->
                        ServiceCalendarDayItem(
                            day = day,
                            isAvailable = availableDates.contains(day.date),
                            isSelected = day.date == selectedDay,
                            onDateSelected = onDateSelected
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCalendarDayItem(
    day: ServiceCalendarDay,
    isSelected: Boolean,
    isAvailable: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    val isToday = day.isToday
    val isPastDate = remember(day.date) { day.date.isBefore(LocalDate.now()) }

    val isEnabled = day.isCurrentMonth && !isPastDate && isAvailable

    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    val textColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimary
        !isEnabled -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
        else -> MaterialTheme.colorScheme.onBackground
    }

    val borderModifier = if (isToday && !isSelected) {
        Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = CircleShape
        )
    } else {
        Modifier
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .then(borderModifier)
                .clickable(enabled = isEnabled) { onDateSelected(day.date) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun generateDaysForMonthFixed(yearMonth: YearMonth): List<ServiceCalendarDay> {
    val days = mutableListOf<ServiceCalendarDay>()
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val dayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

    val prevMonth = yearMonth.minusMonths(1)
    val daysInPrevMonth = prevMonth.lengthOfMonth()

    for (i in 0 until dayOfWeek) {
        days.add(
            ServiceCalendarDay(
                date = prevMonth.atDay(daysInPrevMonth - dayOfWeek + i + 1),
                isCurrentMonth = false,
                isToday = false
            )
        )
    }

    for (i in 1..daysInMonth) {
        val date = yearMonth.atDay(i)
        days.add(
            ServiceCalendarDay(
                date = date,
                isCurrentMonth = true,
                isToday = date == LocalDate.now()
            )
        )
    }

    val totalSlots = 42
    val remainingSlots = totalSlots - days.size
    for (i in 1..remainingSlots) {
        val date = yearMonth.plusMonths(1).atDay(i)
        days.add(ServiceCalendarDay(date, isCurrentMonth = false, isToday = false))
    }

    return days
}