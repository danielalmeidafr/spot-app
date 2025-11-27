package com.example.spot.ui.presentation.main_screen.schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.ui.presentation.main_screen.schedules.components.AppointmentItem
import com.example.spot.ui.presentation.main_screen.schedules.model.ScheduleState
import com.example.spot.ui.presentation.main_screen.schedules.util.generateCalendarDays
import com.example.spot.ui.presentation.main_screen.schedules.viewmodel.ScheduleViewModel
import com.student.R
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<ScheduleViewModel>()
    val state by viewModel.state.collectAsState()
    var selectedMonth by rememberSaveable { mutableIntStateOf(LocalDate.now().monthValue) }
    var selectedYear by rememberSaveable { mutableIntStateOf(LocalDate.now().year) }

    val calendarDays = remember(selectedMonth, selectedYear) {
        generateCalendarDays(selectedYear, selectedMonth)
    }

    LaunchedEffect(selectedMonth, selectedYear) {
        viewModel.appointments(selectedMonth)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 450.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
            when (val state = state) {
                is ScheduleState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(110.dp))

                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onBackground,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                is ScheduleState.Success -> {
                    val appointments = state.appointmentsData
                    if (appointments.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(70.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.schedule),
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = "Calendar Image",
                                modifier = Modifier
                                    .size(60.dp)
                                    .alpha(0.7f)
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Nenhum agendamento marcado",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = when {
                                    selectedYear > LocalDate.now().year ||
                                            (selectedYear == LocalDate.now().year && selectedMonth > LocalDate.now().monthValue) ->
                                        "Agende novos serviços para vê-los aqui"

                                    selectedYear == LocalDate.now().year && selectedMonth == LocalDate.now().monthValue ->
                                        "Agende novos serviços para este mês"

                                    else ->
                                        "Nenhum serviço foi agendado neste mês"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Text(
                                    text = "Seus horários:",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 15.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            items(appointments.size) { index ->
                                AppointmentItem(appointments[index])

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

                is ScheduleState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            CalendarSection(
                selectedMonth = selectedMonth,
                onMonthChange = { selectedMonth = it },
                calendarDays = calendarDays
            )
        }
    }
}