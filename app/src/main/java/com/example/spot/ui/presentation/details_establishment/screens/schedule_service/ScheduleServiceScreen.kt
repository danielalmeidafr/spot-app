package com.example.spot.ui.presentation.details_establishment.screens.schedule_service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.ui.components.PrimaryButton
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.components.AttendantCard
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.components.AvailableTimes
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.components.ServiceCalendarPager
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.components.ServiceCard
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model.ScheduleServiceState
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.viewmodel.ScheduleServiceViewModel
import com.student.R
import java.time.LocalDate
import androidx.compose.foundation.lazy.items

@Composable
fun ScheduleServiceScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val viewModel: ScheduleServiceViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf<String?>(null) }

    when (val state = state) {
        is ScheduleServiceState.Loading -> {

        }

        is ScheduleServiceState.Error -> {

        }

        is ScheduleServiceState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .statusBarsPadding()
                            .padding(start = 20.dp, top = 12.dp, bottom = 13.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .statusBarsPadding()
                                .size(40.dp)
                                .zIndex(2f),
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = CircleShape,
                            onClick = onBack
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "Botão de voltar",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Text(
                            text = "Agendar um serviço",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        items(state.attendants) { attendant ->
                            AttendantCard(
                                attendantInfoData = attendant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.93f)
                            .padding(top = 25.dp)
                    ) {
                        ServiceCalendarPager(
                            selectedDate = selectedDate,
                            onDateSelected = { date ->
                                selectedDate = date
                                selectedTime = null
                            }
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 35.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))

                    AvailableTimes(
                        availableTimesData = state.availableTimes,
                        selectedTime = selectedTime,
                        onTimeSelected = { newTime ->
                            selectedTime = newTime
                        }
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))

                    ServiceCard(
                        serviceInfoData = state.serviceInfo,
                        selectedTime = selectedTime,
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )

                        Text(
                            "Adicionar outro serviço",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(35.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = state.serviceInfo.price,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        PrimaryButton(
                            "Agendar",
                            onClick = {}
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}