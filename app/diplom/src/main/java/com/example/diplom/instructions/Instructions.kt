package com.example.diplom.instructions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Product

@Composable
fun Instructions() {
    var dataList by remember { mutableStateOf<List<Product>>(emptyList()) }
    var selectedItem by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        dataList = Dependencies.repository.getProductList()
    }

    TableContent(
        dataList = dataList,
        onItemClick = {
            selectedItem = it
        }
    )

    selectedItem?.let { data ->
        ProductCard(data = data) {
            selectedItem = null
        }
    }
}

@Composable
fun TableContent(dataList: List<Product>, onItemClick: (Product) -> Unit) {
    LazyColumn {
        items(dataList) { item ->
            TableRow(item = item, onItemClick = onItemClick)
        }
    }
}

@Composable
fun TableRow(item: Product, onItemClick: (Product) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onItemClick(item) }
            .padding(16.dp)
    ) {
        Text(text = item.link, style = MaterialTheme.typography.bodySmall)
        Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}
