package com.example.diplom.instructions

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.diplom.storage.models.Product
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Instructions(viewModel: InstructionsViewModel) {
    val dataList by remember {
        viewModel.dataList
    }

    val selectedItem = remember { mutableStateOf<Product?>(null) }
    val showLink = remember { mutableStateOf<String?>(null) }

    TableContent(
        dataList = dataList,
        onItemClick = {
            selectedItem.value = it
        }
    )

    val linkToOpen = remember {
        viewModel.linkToOpen
    }

    fun openLink() {
        Log.d("openLink", "openLink")
        showLink.value = selectedItem.value?.link
    }

    selectedItem.value?.let {
        Log.d("IT", "${it}")
        val link = it.link
        ProductCard(
            data = it,
            onLinkClick = { openLink() } ,
            onClose = { selectedItem.value = null }
        )
    }

    showLink.value?.let {link ->
        viewModel.saveLog(Calendar.getInstance().time, link)
        Column() {
            TopAppBar(
                title = { Text(text = "") },
                modifier = Modifier.background(Color.Red),
                navigationIcon = {
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { showLink.value = null }) {
                        Text(text = "Close")
                    }
                }
            )
            WebViewPage(link)
        }
    }
}

@Composable
fun WebViewPage(url: String) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
        }
    }
    Box {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { webView },
            update = {
                it.loadUrl(url)
            }
        )
    }
}

@Composable
fun TableContent(dataList: List<Product>, onItemClick: (Product) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .testTag("table")
            .background(Color.White)
    ) {
        items(dataList) { item ->
            TableRow(item = item, onItemClick = { onItemClick(item) })
        }
    }
}

@Composable
fun TableRow(item: Product, onItemClick: (Product) -> Unit) {
    Column(
        modifier = Modifier
            .clickable {
                onItemClick(item)
            }
            .padding(16.dp)
            .testTag("tableItem")
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = item.description, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(2.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}
