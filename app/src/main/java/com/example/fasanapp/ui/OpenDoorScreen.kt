package com.example.fasanapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fasanapp.mvvm.UserViewModel

@Composable
fun OpenDoorScreen(vm: UserViewModel) {
    val state by vm.open.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!state) {
            Button(
                onClick = { vm.open() },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(234, 240, 246),
                    contentColor = Color(66, 133, 244)
                )
            ) {
                Icon(
                    Icons.Outlined.Lock,
                    contentDescription = "locked",
                    modifier = Modifier
                        .size(
                            width = 200.dp,
                            height = 200.dp
                        )
                )
            }
        } else {
            Button(
                onClick = { vm.open() },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(234, 240, 246),
                    contentColor = Color(240, 98, 146, 255)
                )


            ) {
                Icon(
                    Icons.Outlined.Warning,
                    contentDescription = "unlocked",
                    modifier = Modifier
                        .size(
                            width = 200.dp,
                            height = 200.dp
                        )
                )

            }
        }
    }
}

@Preview()
@Composable
fun OpenDoorScreenPreview() {
    OpenDoorScreen(vm = UserViewModel())
}