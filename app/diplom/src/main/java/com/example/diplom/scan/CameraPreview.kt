package com.example.diplom.scan

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.diplom.navigation.Routes
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Product
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreview(navController: NavHostController) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var showCamera by remember { mutableStateOf<Boolean>(false) }

    cameraPermissionState.let {
        if (it.hasPermission) { showCamera = true }
    }

    showCamera.let { hasPermission ->
        if (hasPermission) {
            Column(modifier = Modifier.fillMaxSize()) {
                Camera(navController = navController)
            }
        } else {
            Button(onClick = {
                cameraPermissionState.launchPermissionRequest()
            }) {
                Text("Разрешить доступ к камере")
            }
        }
    }
}

@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun Camera(navController: NavHostController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    var showNewProductCard by remember { mutableStateOf<String?>(null) }
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
                    navController.navigate(Routes.Menu.route)
                                },
                onCloseClicked = {
                    showNewProductCard = null
                    navController.navigate(Routes.Menu.route)
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
        update = { previewView ->
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                    barcodes.firstNotNullOf { barcode ->
                        barcode.rawValue.let { barcodeValue ->
//                            barCodeVal.value = barcodeValue ?: "no value!!!"
                            if (showNewProductCard == null) {
                                showNewProductCard = barcodeValue
                            }
                        }
                    }
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}