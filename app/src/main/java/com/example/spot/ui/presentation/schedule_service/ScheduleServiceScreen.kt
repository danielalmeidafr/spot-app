package com.example.spot.ui.presentation.schedule_service

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.spot.ui.components.PrimaryButton
import com.example.spot.ui.presentation.schedule_service.components.AttendantCard
import com.example.spot.ui.presentation.schedule_service.components.AvailableHours
import com.example.spot.ui.presentation.schedule_service.components.ServiceCalendarPager
import com.example.spot.ui.presentation.schedule_service.components.ServiceCard
import com.example.spot.ui.presentation.schedule_service.model.AttendantInfoData
import com.example.spot.ui.presentation.schedule_service.model.ScheduleServiceState
import com.example.spot.ui.presentation.schedule_service.viewmodel.ScheduleServiceViewModel
import com.student.R
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun ScheduleServiceScreen(
    modifier: Modifier = Modifier,
    establishmentId: String,
    serviceId: String,
    onBack: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToConfirmPayment: (String, String) -> Unit
) {
    val viewModel: ScheduleServiceViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    var selectedDay by remember { mutableStateOf(LocalDate.now()) }
    var selectedHour by remember { mutableStateOf<String?>(null) }
    var selectedAttendant by remember { mutableStateOf<AttendantInfoData?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadSchedule(establishmentId, serviceId)
    }

    LaunchedEffect(state) {
        if (state is ScheduleServiceState.Success && selectedAttendant == null) {
            selectedAttendant = (state as ScheduleServiceState.Success).attendants.firstOrNull()
        }
    }

    when (val currentState = state) {
        is ScheduleServiceState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Carregando...")
            }
        }

        is ScheduleServiceState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = currentState.message)
            }
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
                            .padding(start = 30.dp, top = 12.dp, bottom = 13.dp),
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
                    if (currentState.attendants.isNotEmpty()){
                        Spacer(modifier = Modifier.height(25.dp))

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            items(currentState.attendants) { attendant ->
                                val isSelected = selectedAttendant?.id == attendant.id

                                val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                        .clickable { selectedAttendant = attendant }
                                        .padding(10.dp)
                                ) {
                                    AttendantCard(
                                        attendantInfoData = attendant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.93f)
                            .padding(top = 25.dp)
                    ) {
                        ServiceCalendarPager(
                            selectedDay = selectedDay,
                            availableDates = currentState.availableDays,
                            onDateSelected = { date ->
                                selectedDay = date
                                selectedHour = null
                                viewModel.onDateSelected(date)
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

                    AvailableHours(
                        availableHoursData = currentState.availableTimes,
                        selectedHour = selectedHour,
                        onTimeSelected = { newTime ->
                            selectedHour = newTime
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
                        serviceInfoData = currentState.serviceInfo,
                        selectedTime = selectedHour,
                        nameAttendant = selectedAttendant?.name ?: "Selecione um profissional",
                        attendantImage = selectedAttendant?.profileImage
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(35.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Total:",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                text = "R$ ${currentState.totalPrice}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        PrimaryButton(
                            "Ir para pagamento",
                            onClick = {
                                if(selectedHour != null && selectedAttendant != null) {
                                    viewModel.attemptPaymentNavigation(
                                        onNavigate = { onNavigateToConfirmPayment(establishmentId, serviceId) }
                                    )
                                }
                            }
                        )

                        if (currentState.showLoginDialog) {
                            AlertDialog(
                                onDismissRequest = { viewModel.dismissLoginDialog() },
                                title = {
                                    Text(
                                        text = "Faça login",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                },
                                text = {
                                    Text(
                                        text = "Você precisa estar logado para agendar este serviço. Deseja entrar agora?",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp),
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            viewModel.dismissLoginDialog()
                                            onNavigateToSignIn()
                                        }
                                    ) {
                                        Text(
                                            text = "Entrar",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { viewModel.dismissLoginDialog() }
                                    ) {
                                        Text(
                                            text = "Cancelar",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                },
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}