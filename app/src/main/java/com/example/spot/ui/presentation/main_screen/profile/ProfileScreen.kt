package com.example.spot.ui.presentation.main_screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.ui.presentation.main_screen.profile.components.ProfileCard
import com.example.spot.ui.presentation.main_screen.profile.components.ProfileListItem
import com.example.spot.ui.presentation.main_screen.profile.components.ProfileProgressBar
import com.student.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val isLogged: Boolean = true

    if (isLogged) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 30.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    if (isSystemInDarkTheme()) {
                        R.drawable.logo_dark
                    } else {
                        R.drawable.logo_light
                    }
                ),
                contentDescription = "Profile image",
                modifier = Modifier
                    .size(150.dp)
            )

            Text(
                "Bem-vindo ao Spot!",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Crie sua conta e comece a agendar com os melhores profissionais da região em segundos.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(20.dp))

            BenefitItem(text = "Agende 24 horas por dia, 7 dias por semana.")
            BenefitItem(text = "Gerencie seus horários e lembretes.")
            BenefitItem(text = "Salve seus estabelecimentos favoritos.")

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Entrar",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                "Continuar como convidado",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.clickable { }
            )
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(
                top = 15.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "PERFIL",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Icon(
                        painter = painterResource(
                            if (isSystemInDarkTheme()) {
                                R.drawable.settings_dark
                            } else {
                                R.drawable.settings_light
                            }
                        ),
                        contentDescription = "Settings image",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 20.dp)
                            .size(18.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.user_image),
                        contentDescription = "Profile image",
                        modifier = Modifier.size(90.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "Daniel Alves Almeida",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        "@danielalmeidafr",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))

                ProfileProgressBar(
                    currentVisits = 4,
                    goalVisits = 5,
                    rewardText = "10% OFF no próximo serviço",
                )
            }

            item {
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.95f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ProfileCard(
                        iconLight = R.drawable.schedule_profile_light,
                        iconDark = R.drawable.schedule_profile_dark,
                        value = "4",
                        label = "Agendamentos"
                    )

                    ProfileCard(
                        iconLight = R.drawable.favorite_light,
                        iconDark = R.drawable.favorite_dark,
                        value = "5",
                        label = "Favoritos"
                    )

                    ProfileCard(
                        iconLight = R.drawable.reviews_light,
                        iconDark = R.drawable.reviews_dark,
                        value = "12",
                        label = "Avaliações"
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))

                ProfileListItem(
                    text = "Editar perfil",
                    iconDark = R.drawable.user_edit_dark,
                    iconLight = R.drawable.user_edit_light,
                )

                Spacer(modifier = Modifier.height(15.dp))

                ProfileListItem(
                    text = "Segurança e senha",
                    iconDark = R.drawable.security_dark,
                    iconLight = R.drawable.security_light,
                )

                Spacer(modifier = Modifier.height(15.dp))

                ProfileListItem(
                    text = "Métodos de pagamentos",
                    iconDark = R.drawable.wallet_dark,
                    iconLight = R.drawable.wallet_light,
                )

                Spacer(modifier = Modifier.height(30.dp))

                ProfileListItem(
                    text = "Notificações",
                    iconDark = R.drawable.user_edit_dark,
                    iconLight = R.drawable.user_edit_light,
                )

                Spacer(modifier = Modifier.height(15.dp))

                ProfileListItem(
                    text = "Ajuda e suporte",
                    iconDark = R.drawable.security_dark,
                    iconLight = R.drawable.security_light,
                )

                Spacer(modifier = Modifier.height(30.dp))

                ProfileListItem(
                    text = "Sair",
                    isLogout = true
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun BenefitItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "•",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(20.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}