package com.example.spot.ui.presentation.confirm_payment.success_confirm_payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.spot.core.theme.SpotTheme
import com.example.spot.ui.components.PrimaryButton
import com.student.R

@Composable
fun SuccessfulConfirmPayment(
    onNavigateToMain: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = R.raw.success)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(140.dp)
            )

            Text(
                "Pagamento confirmado!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                "O pagamento foi efetuado com sucesso. Aguarde até a data escolhida para o serviço.",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(0.9f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Continuar",
            onClick = onNavigateToMain
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}