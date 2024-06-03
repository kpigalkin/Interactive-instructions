package com.example.diplom.scan

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.diplom.navigation.Routes
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Product
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanViewModel(navController: NavHostController): ViewModel() {

    private val _showCamera = mutableStateOf<Boolean?>(null)
    val showCamera: State<Boolean?> get () = _showCamera

    private val _showCard = mutableStateOf<Boolean?>(null)
    val showCard: State<Boolean?> get () = _showCard

    private val _detectedLink = mutableStateOf<String?>(null)
    val detectedLink: State<String?> get () = _detectedLink

    val cameraPermissionState by mutableStateOf(Manifest.permission.CAMERA)

    private var navController: NavHostController

    init {
        this.navController = navController
    }

    fun onLinkDetection(link: String?) {
        _detectedLink.value = link
    }

    fun onAction() {
        navController.navigate(Routes.Menu.route)
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun onPermission(state: PermissionState) {
        if (state.hasPermission) {
            _showCamera.value = true
        }
    }

    fun startCameraPreview(context: Context, lifecycleOwner: LifecycleOwner, previewView: PreviewView) {
        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                barcodes.firstNotNullOf { barcode ->
                    barcode.rawValue.let { barcodeValue ->
                        Log.d("analyze", "DETECTED")
                        onLinkDetection(barcodeValue)
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

    fun onAddProductClick(product: Product) {
        viewModelScope.launch {
            Dependencies.repository.insertProduct(product)
        }
        _showCard.value = null
    }

    fun onOpenCardClick() {
//        _showCard.value = null
        _showCard.value = true
    }
}