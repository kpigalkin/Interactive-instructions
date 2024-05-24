package com.example.diplom.menu

import MenuViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun Menu(viewModel: MenuViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Column {
            Button(
                modifier = Modifier.testTag("cameraButto"),
                onClick = {
                viewModel.onScanButtonClick()
            }) {
                Text(text = "Scan QR-code", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.testTag("instrButton"),
                onClick = {
                viewModel.onInstructionsButtonClick()
            }) {
                Text(text = "Instructions database", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.testTag("logsButton"),
                onClick = {
                viewModel.onLogsButtonClick()
            }) {
                Text(text = "Logs", color = Color.White)
            }
        }
    }
}