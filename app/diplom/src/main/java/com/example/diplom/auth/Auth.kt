package com.example.diplom.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Auth(viewModel: AuthViewModel) {
    val hintText = viewModel.hintText.value
    val buttonText = viewModel.buttonText.value

    val errorState by remember {
        viewModel.errorState
    }

    errorState?.let {
        ThrowAlert(message = it) {
            viewModel.alertButtonClicked()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AuthTitle()
        AuthFields(
            login = viewModel.login,
            password = viewModel.password,
            setLogin = { viewModel.login = it },
            setPassword = { viewModel.password = it },
            hintText = hintText,
            onHintClick = { viewModel.hintButtonClicked() }
            )
        AuthSignButton(buttonText) {
            viewModel.buttonClicked()
        }
    }
}

@Composable
fun AuthTitle() {
    Text(
        text = "Interactive\ninstructions",
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 2,
        lineHeight = 40.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top=20.dp, bottom = 20.dp)
    )
}

@Composable
fun ColumnScope.AuthFields(
    login: String,
    password: String,
    setLogin: (String) -> Unit,
    setPassword: (String) -> Unit,
    hintText: String,
    onHintClick: () -> Unit) {
    AuthField(
        value = login,
        onValueChange = setLogin,
        label = { Text("Enter your email") },
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        testTag = "loginForm"
    )

    AuthField(
        value = password,
        onValueChange = setPassword,
        label = { Text("Enter your password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        testTag = "passwordForm"
    )

    TextButton(
        onClick = { onHintClick() },
        modifier = Modifier
            .padding(bottom = 12.dp)
            .align(Alignment.End)
    ) {
        Text(hintText)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    visualTransformation: VisualTransformation = PasswordVisualTransformation(),
    keyboardOptions: KeyboardOptions,
    testTag: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        modifier = Modifier.testTag(testTag)
    )
}

@Composable
fun AuthSignButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .testTag("signInButton")
            .fillMaxWidth()
    ) {
        Text(text)
    }
}

@Composable
fun ThrowAlert(message: String, onClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onClick },
        title = {
            Text(message)
        },
        confirmButton = {
            Button(onClick = onClick) {
                Text("Try again")
            }
        },
        modifier = Modifier.testTag("errorMessage")
    )
}

