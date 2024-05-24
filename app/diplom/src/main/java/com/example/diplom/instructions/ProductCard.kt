package com.example.diplom.instructions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Detail
import com.example.diplom.storage.models.Product
import com.example.diplom.storage.models.ProductDetails


data class TableModel(
    val detailName: String,
    val required: Int,
    val available: Int
)

@Composable
fun ProductCard(
    data: Product,
    onLinkClick: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable(onClick = { onClose() })
            .testTag("card")
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .size(350.dp)
                .background(Color.White)
        ) {
            Column(modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center)
            ) {
                Text(
                    text = "${data.description}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                OpenInstructionButton(url = data.link, onLinkClick = { onLinkClick() } )
                Spacer(modifier = Modifier.height(2.dp))
                ProductDetailContent(dataList = getDetailsData(forProduct = data))
            }
        }
    }
}

@Composable
fun OpenInstructionButton(url: String, onLinkClick: () -> Unit) {
    Button(
        onClick = { onLinkClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp),
        colors = ButtonDefaults.run { buttonColors(Color.Blue) },
    ) {
        Text(
            text = "Open instruction",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}


@Composable
fun ProductDetailContent(dataList: List<TableModel>) {
    val nameColomnWidth = .4f
    val requiredColomnWidth = .3f
    val availableColomnWidth = .3f

    LazyColumn {
        item {
            Row(Modifier.background(Color.LightGray)) {
                ProductDetailRow(text = "Наименование", weight = nameColomnWidth, style = MaterialTheme.typography.bodyMedium)
                ProductDetailRow(text = "Сборка", weight = requiredColomnWidth, style = MaterialTheme.typography.bodyMedium)
                ProductDetailRow(text = "На складе", weight = availableColomnWidth, style = MaterialTheme.typography.bodyMedium)
            }
        }
        items(dataList) { item ->
            val color = if (item.available >= item.required) Color.Green else Color.Red
            Row(Modifier.background(color)) {
                ProductDetailRow(text = item.detailName, weight = nameColomnWidth)
                ProductDetailRow(text = "${item.required}", weight = requiredColomnWidth)
                ProductDetailRow(text = "${item.available}", weight = availableColomnWidth)
            }
        }
    }
}

@Composable
fun RowScope.ProductDetailRow(
    text: String,
    weight: Float,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodySmall
) {
    Text(
        text = text,
        style = style,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .weight(weight)
    )
}

@Composable
fun getDetailsData(forProduct: Product): List<TableModel> {
    var productDetails by remember { mutableStateOf<List<ProductDetails>>(emptyList()) }
    var details by remember { mutableStateOf<List<Detail>>(emptyList()) }

    LaunchedEffect(Unit) {
        productDetails = Dependencies.repository.getProductDetailsList()
        details = Dependencies.repository.getDetailsList()
    }

    return productDetails
        .filter {
            it.idProduct == forProduct.id
        }
        .map { product ->
            val detail = details.firstOrNull { product.idDetail == it.id }
            TableModel(
                detailName = detail?.title ?: "not find",
                required = product.count,
                available = detail?.count ?: 0
            )
        }
}