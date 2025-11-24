package com.example.spot.ui.presentation.main_screen.schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.schedules.util.CalendarDay
import java.time.LocalDate

private val WEEK = listOf("DOM.", "SSEG.", "TER.", "QUA.", "QUI.", "SEX.", "SAB.")
private val MONTHS = listOf(
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
)
private val MONTHS_ABBREVIATION = listOf(
    "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"
)

@Composable
fun CalendarSection(
    selectedMonth: Int,
    onMonthChange: (Int) -> Unit,
    calendarDays: List<CalendarDay>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 15.dp, start = 5.dp, end = 5.dp)
    ) {
        Text(
            text = MONTHS[selectedMonth - 1],
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(20.dp))

        WeekHeader()

        Spacer(modifier = Modifier.height(20.dp))

        CalendarGrid(calendarDays)


        MonthSelector(selectedMonth = selectedMonth, onMonthChange = onMonthChange)

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun WeekHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        WEEK.forEach { wd ->
            Text(
                text = wd,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CalendarGrid(calendarDays: List<CalendarDay>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(235.dp)
    ) {
        items(calendarDays, key = { it.date }) { day ->
            CalendarDayItem(day = day)
        }
    }
}

@Composable
private fun CalendarDayItem(day: CalendarDay) {
    val isToday = remember(day) { day.date == LocalDate.now() }
    val color = if (day.inMonth)
        MaterialTheme.colorScheme.onBackground
    else
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)

    Box(
        modifier = Modifier.size(28.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isToday) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(50)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = color,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MonthSelector(
    selectedMonth: Int,
    onMonthChange: (Int) -> Unit
) {
    val monthListState = rememberLazyListState()
    LaunchedEffect(selectedMonth) {
        monthListState.animateScrollToItem(selectedMonth - 1)
    }

    LazyRow(
        state = monthListState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(23.dp)
    ) {
        items(MONTHS_ABBREVIATION.size) { index ->
            val monthNumber = index + 1
            val selected = monthNumber == selectedMonth

            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceContainer
                    )
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .clickable { onMonthChange(monthNumber) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = MONTHS_ABBREVIATION[index],
                    style = MaterialTheme.typography.bodySmall,
                    color = if (selected)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}