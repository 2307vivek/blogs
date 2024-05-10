package inn.vivvvek.blogs.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onClickTrailingIcon: () -> Unit,
    supportingText: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = label) },
        isError = isError,
        visualTransformation = visualTransformation,
        supportingText = supportingText,
        trailingIcon = {
            val icon = if (isPasswordVisible) {
                Icons.Filled.VisibilityOff
            } else Icons.Filled.Visibility

            val description = if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = onClickTrailingIcon) {
                Icon(
                    imageVector = icon,
                    contentDescription = description
                )
            }
        }
    )
}