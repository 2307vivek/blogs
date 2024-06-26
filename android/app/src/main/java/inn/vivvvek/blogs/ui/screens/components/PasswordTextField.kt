/*
 * Copyright 2024 Vivek Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
