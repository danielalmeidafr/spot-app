import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.spot.ui.presentation.components.CustomTextField

@Composable
fun DateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Data de nascimento:"
) {
    fun formatDate(input: String): String {
        val digits = input.filter { it.isDigit() }.take(8)
        return buildString {
            for (i in digits.indices) {
                append(digits[i])
                if (i == 2 || i == 4) append('/')
            }
        }
    }

    CustomTextField(
        value = value,
        onValueChange = { onValueChange(formatDate(it)) },
        placeholderText = placeholderText,
        modifier = modifier
    )
}
