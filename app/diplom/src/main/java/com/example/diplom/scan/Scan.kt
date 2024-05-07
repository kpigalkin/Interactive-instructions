//package com.example.diplom.scan
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
//
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun Scan() {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Bottom
//    ) {
//        Spacer(modifier = Modifier.height(10.dp))
//
//        val cameraPermissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)
//        Button(
//            onClick = {
//                cameraPermissionState.launchPermissionRequest()
//            }
//        ) {
//            Text(text = "Camera Permission")
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        CameraPreview()
//    }
//}
//
//
