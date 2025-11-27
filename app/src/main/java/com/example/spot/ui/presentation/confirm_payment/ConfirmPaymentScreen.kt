package com.example.spot.ui.presentation.confirm_payment

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.spot.data.remote.dtos.details.PaymentMethods
import com.example.spot.ui.components.PrimaryButton
import com.example.spot.ui.presentation.confirm_payment.components.AddPaymentCard
import com.example.spot.ui.presentation.confirm_payment.components.PaymentMethodCard
import com.example.spot.ui.presentation.schedule_service.model.ScheduleServiceState
import com.example.spot.ui.presentation.schedule_service.viewmodel.ScheduleServiceViewModel
import com.student.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfirmPaymentScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigateToSuccessFullConfirmPayment: () -> Unit,
    attendantId:String,
    establishmentId: String,
    offeredServiceId: String,
    scheduledAt: String
) {
    val viewModel: ScheduleServiceViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadSchedule(establishmentId, offeredServiceId)
    }

    when (val currentState = state) {
        is ScheduleServiceState.PaymentSuccess -> onNavigateToSuccessFullConfirmPayment()

        is ScheduleServiceState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ScheduleServiceState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(currentState.message)
            }
        }

        is ScheduleServiceState.Success -> {
            var selectedPaymentId by remember { mutableStateOf(currentState.payments.firstOrNull()!!.id) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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
                        text = "Confirmar pagamento",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.weight(1f))

                currentState.payments.forEach { payment ->

                    val (text, icon) = getPaymentMethodDetails(payment.type)

                    PaymentMethodCard(
                        text = text,
                        agreed = selectedPaymentId == payment.id,
                        icon = icon,
                        onToggle = {
                            selectedPaymentId = payment.id
                        }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }

                AddPaymentCard(
                    onClick = {}
                )

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Confirmar Pagamento",
                    onClick = {
                        viewModel.attemptPaymentNavigation {
                            viewModel.onConfirmPaymentClicked(attendantId, establishmentId, offeredServiceId, selectedPaymentId, scheduledAt)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

fun getPaymentMethodDetails(type: PaymentMethods): Triple<String, PaymentIcon, Boolean> {
    return when (type) {
        PaymentMethods.CASH -> Triple("Pagar no local", PaymentIcon.ImageRes(R.drawable.dollar), false)
        PaymentMethods.PIX -> Triple("Pix", PaymentIcon.ImageRes(R.drawable.pix), false)
        PaymentMethods.CARD -> Triple("Meu cartão (débito/crédito)", PaymentIcon.VectorRes(R.drawable.card), true)
    }
}

sealed class PaymentIcon {
    data class ImageRes(val res: Int) : PaymentIcon()
    data class VectorRes(val res: Int) : PaymentIcon()
}