package com.example.diplom.scan

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.diplom.storage.models.Product
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Scan(viewModel: ScanViewModel) {
    val cameraPermissionState = rememberPermissionState(permission = viewModel.cameraPermissionState)
    val showCamera = remember { viewModel.showCamera }
    val detectedLink = remember { viewModel.detectedLink }

    cameraPermissionState.let {
        viewModel.onPermission(it)
    }

    showCamera.let {
        if (it.value == true) {
            Column(modifier = Modifier.fillMaxSize()) {
                Camera(viewModel)
            }
        } else {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                PermissionButton(cameraPermissionState)
            }
        }
    }
    detectedLink.value?.let {
        OpenNewCard(viewModel = viewModel, link = it)
    }
}

@Composable
fun OpenNewCard(viewModel: ScanViewModel, link: String) {
    NewProductCard(
        link = link,
        onSaveClicked = {
            val newProduct = Product(link = link, description = it)
            viewModel.onAddProductClick(newProduct)
            viewModel.onAction() },
        onCloseClicked = {
            viewModel.onAction()
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionButton(cameraPermissionState: PermissionState) {
    Button(onClick = {
        cameraPermissionState.launchPermissionRequest()
    }) {
        Text("Разрешить доступ к камере")
    }
}

@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun Camera(viewModel: ScanViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Column {
        AndroidView(
            factory = { androidViewContext ->
                PreviewView(androidViewContext).apply {
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            update = {
                viewModel.startCameraPreview(context, lifecycleOwner, it)
            }
        )
    }
}