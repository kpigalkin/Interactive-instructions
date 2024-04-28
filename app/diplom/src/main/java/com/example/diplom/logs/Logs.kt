package com.example.diplom.logs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun Logs() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Logs here",
            style = TextStyle(fontSize = 24.sp, color = Color.Red)
        )
    }
}