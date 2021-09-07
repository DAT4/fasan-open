package sh.mama.fasanapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import sh.mama.fasanapp.models.User
import sh.mama.fasanapp.mvvm.UserViewModel

@Composable
fun LoginScreen(vm: UserViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            modifier = modifier,
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = ""
                )
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = modifier,
            leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = ""
                )
            }

        )
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        Button(
            onClick = { vm.manualLogin(User(username, password)) },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(234, 240, 246),
                backgroundColor = Color(66, 133, 244)

            ),
        ) {
            Text(text = "LOGIN")
        }
    }
}