package com.example.diplom.scan

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Product
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Scan(viewModel: ScanViewModel) {
    val cameraPermissionState = rememberPermissionState(permission = viewModel.cameraPermissionState)
    var showCamera = remember { viewModel.showCamera }

    cameraPermissionState.let {
        viewModel.onPermission(it)
    }

    showCamera.let {
        if (it) {
            Column(modifier = Modifier.fillMaxSize()) {
                Camera(viewModel)
            }
        } else {
            PermissionButton(cameraPermissionState)
        }
    }
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
    var preview by remember { mutableStateOf<Preview?>(null) }
    var showNewProductCard = remember { viewModel.showNewProductCard }
    val coroutineScope = rememberCoroutineScope()

    showNewProductCard.let { link ->
        if (link != null) {
            NewProductCard(
                link = link,
                onSaveClicked = { descr ->
                    val newProduct = Product(link = link, description = descr)
                    coroutineScope.launch {
                        Dependencies.repository.insertProduct(newProduct)
                    }
                    showNewProductCard = null
                    viewModel.onAction()
                                },
                onCloseClicked = {
                    showNewProductCard = null
                    viewModel.onAction()
                }
            )
        }
    }

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