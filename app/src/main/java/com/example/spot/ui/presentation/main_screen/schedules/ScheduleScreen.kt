package com.example.spot.ui.presentation.main_screen.schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.ui.presentation.main_screen.schedules.components.AppointmentItem
import com.example.spot.ui.presentation.main_screen.schedules.components.AppointmentItemSkeleton
import com.example.spot.ui.presentation.main_screen.schedules.model.ScheduleState
import com.example.spot.ui.presentation.main_screen.schedules.util.generateCalendarDays
import com.example.spot.ui.presentation.main_screen.schedules.viewmodel.ScheduleViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var selectedMonth by rememberSaveable { mutableIntStateOf(LocalDate.now().monthValue) }
    var selectedYear by rememberSaveable { mutableIntStateOf(LocalDate.now().year) }

    val calendarDays = remember(selectedMonth, selectedYear) {
        generateCalendarDays(selectedYear, selectedMonth)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 450.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
            when (state) {
                is ScheduleState.Loading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        repeat(4) { AppointmentItemSkeleton() }
                    }
                }

                is ScheduleState.Success -> {
                    val appointments = (state as ScheduleState.Success).appointmentsData
                    if (appointments.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Nenhum agendamento marcado")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
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
                            }
                        }
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