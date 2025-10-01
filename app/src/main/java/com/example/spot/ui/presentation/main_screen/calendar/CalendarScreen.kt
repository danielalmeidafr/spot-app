package com.example.spot.ui.presentation.main_screen.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.calendar.components.AppointmentItem
import com.student.R
import java.time.LocalDate
import java.time.YearMonth

private val WEEK = listOf("D", "S", "T", "Q", "Q", "S", "S")
private val MONTHS = listOf(
    "Janeiro",
    "Fevereiro",
    "Março",
    "Abril",
    "Maio",
    "Junho",
    "Julho",
    "Agosto",
    "Setembro",
    "Outubro",
    "Novembro",
    "Dezembro"
)
private val MONTHS_ABBREVIATION = listOf(
    "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"
)

private data class CalendarDay(val date: LocalDate, val inMonth: Boolean)

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
    while (days.size % 7 != 0) days.add(CalendarDay(nextYm.atDay(nextDay++), false))
    while (days.size < 42) days.add(CalendarDay(nextYm.atDay(nextDay++), false))

    return days
}

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isDarkTheme = isSystemInDarkTheme()
    val uiState by viewModel.uiState.collectAsState()

    val calendarDays = remember(uiState.year, uiState.month) {
        generateCalendarDays(uiState.year, uiState.month)
    }

    val monthTitle = MONTHS[uiState.month - 1]

    LaunchedEffect(Unit) {
        viewModel.loadAppointments()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(top = 50.dp, start = 5.dp, end = 5.dp)) {
                Text(
                    text = monthTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(20.dp))

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

                Spacer(modifier = Modifier.height(20.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                ) {
                    items(calendarDays) { day ->
                        val isToday = day.date == LocalDate.now()
                        val color = if (day.inMonth) MaterialTheme.colorScheme.onBackground
                        else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)

                        Box(modifier = Modifier.size(28.dp), contentAlignment = Alignment.Center) {
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
                }

                val monthListState = rememberLazyListState()
                LaunchedEffect(Unit) { monthListState.scrollToItem(uiState.month - 1) }

                LazyRow(
                    state = monthListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(23.dp)
                ) {
                    itemsIndexed(MONTHS_ABBREVIATION) { index, shortName ->
                        val monthNumber = index + 1
                        val selected = monthNumber == uiState.month

                        Box(
                            modifier = Modifier
                                .size(width = 60.dp, height = 30.dp)
                                .shadow(
                                    elevation = 0.3.dp,
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
                                .clickable { viewModel.selectMonth(monthNumber) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = shortName,
                                style = MaterialTheme.typography.bodySmall,
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
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                        .width(67.dp)
                        .height(2.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )

                val appointments = uiState.appointments
                val isLoading = uiState.isLoading

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp)
                ) {
                    Spacer(modifier = Modifier.height(70.dp))
                    if (appointments.isNotEmpty()) {
                        Text(
                            text = "Seus horários:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when {
                        isLoading -> {
                            Spacer(modifier = Modifier.height(50.dp))
                        }

                        appointments.isEmpty() -> {
                            val calendarRes =
                                if (isDarkTheme) R.drawable.calendar_dark else R.drawable.calendar_light

                            Image(
                                painter = painterResource(id = calendarRes),
                                contentDescription = "Calendar Image",
                                modifier = Modifier.size(60.dp),
                                alpha = 0.7f
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Nenhum agendamento marcado",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Agende novos serviços para vê-los aqui",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(30.dp))
                        }

                        else -> {
                            appointments.forEachIndexed { index, appointment ->
                                AppointmentItem(
                                    appointment = appointment,
                                    isDarkTheme = isDarkTheme
                                )

                                if (index < appointments.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier.fillMaxWidth(0.9f),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}