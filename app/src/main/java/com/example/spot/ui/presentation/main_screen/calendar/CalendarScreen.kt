package com.example.spot.ui.presentation.main_screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

private val WEEK = listOf("D", "S", "T", "Q", "Q", "S", "S")
private val MONTHS = listOf(
    "Janeiro", "Fevereiro", "Março", "Abril",
    "Maio", "Junho", "Julho", "Agosto",
    "Setembro", "Outubro", "Novembro", "Dezembro"
)
private val MONTHS_ABBREVIATION = listOf(
    "Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
    "Jul", "Ago", "Set", "Out", "Nov", "Dez"
)

private data class CalendarDay(val date: LocalDate, val inMonth: Boolean)
@RequiresApi(Build.VERSION_CODES.O)
private fun generateCalendarDays(year: Int, month: Int): List<CalendarDay> {
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
    while (days.size % 7 != 0) {
        days.add(CalendarDay(nextYm.atDay(nextDay++), false))
    }

    while (days.size < 42) {
        days.add(CalendarDay(nextYm.atDay(nextDay++), false))
    }

    return days
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    initialYear: Int = LocalDate.now().year,
    initialMonth: Int = LocalDate.now().monthValue
) {
    var selectedYear by rememberSaveable { mutableIntStateOf(initialYear) }
    var selectedMonth by rememberSaveable { mutableIntStateOf(initialMonth) }

    val calendarDays = remember(selectedYear, selectedMonth) {
        generateCalendarDays(selectedYear, selectedMonth)
    }

    val monthTitle = MONTHS[selectedMonth - 1]

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = monthTitle,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WEEK.forEach { wd ->
                        Text(
                            text = wd,
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                ) {
                    items(calendarDays) { day ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val color = if (day.inMonth) {
                                MaterialTheme.colorScheme.onBackground
                            } else {
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            }
                            Text(
                                text = day.date.dayOfMonth.toString(),
                                style = MaterialTheme.typography.displaySmall,
                                color = color,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                val monthListState = rememberLazyListState()
                val initialMonthIndex = selectedMonth - 1

                LaunchedEffect(Unit) {
                    monthListState.scrollToItem(initialMonthIndex)
                }

                LazyRow(
                    state = monthListState,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(23.dp)
                ) {
                    itemsIndexed(MONTHS_ABBREVIATION) { index, shortName ->
                        val monthNumber = index + 1
                        val selected = monthNumber == selectedMonth

                        Box(
                            modifier = Modifier
                                .size(width = 60.dp, height = 30.dp)
                                .shadow(
                                    elevation = 0.5.dp,
                                    shape = RoundedCornerShape(5.dp),
                                    clip = false
                                )
                                .clip(RoundedCornerShape(5.dp))
                                .background(
                                    if (selected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceContainer
                                )
                                .border(
                                    width = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .clickable {
                                    selectedMonth = monthNumber
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = shortName,
                                style = MaterialTheme.typography.displaySmall,
                                color = if (selected) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                        .width(67.dp)
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Nenhum agendamento marcado",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

