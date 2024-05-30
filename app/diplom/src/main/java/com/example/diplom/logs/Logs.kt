package com.example.diplom.logs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun Logs(viewModel: LogsViewModel) {
    val dataList by remember {
        viewModel.dataList
    }

    TableContent(dataList)
}

@Composable
fun TableContent(dataList: List<com.example.diplom.storage.models.Log>) {
    LazyColumn(
        modifier = Modifier
            .testTag("table")
            .background(Color.White)
    ) {
        items(dataList) { item ->
            TableRow(item)
        }
    }
}

@Composable
fun TableRow(item: com.example.diplom.storage.models.Log) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .testTag("tableItem")
    ) {
        Spacer(modifier = Modifier.height(1.dp))
        Text(text = item.date.toString(), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = item.link, style = MaterialTheme.typography.titleMedium)
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}
