package com.example.cognitrix.pages

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(onSignUpClick: (User) -> Unit) {
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val passwordBasedAuth = remember { mutableStateOf(true) }
    val address = remember { mutableStateOf("") }
    val mobile = remember { mutableStateOf("") }
    val profilePicUri = remember { mutableStateOf<Uri?>(null) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Sign Up", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF0D9A90),
                titleContentColor = Color.White
            )
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(it)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Username
                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = { Text("Username") },
                        isError = username.value.length < 6,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Email
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email*") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // First Name
                    OutlinedTextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        label = { Text("First Name*") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Last Name
                    OutlinedTextField(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        label = { Text("Last Name*") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Password
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Password Confirmation
                    OutlinedTextField(
                        value = confirmPassword.value,
                        onValueChange = { confirmPassword.value = it },
                        label = { Text("Password confirmation") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Password-based authentication
                    Text(text = "Password-based authentication")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = passwordBasedAuth.value,
                            onClick = { passwordBasedAuth.value = true }
                        )
                        Text(text = "Enabled")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(
                            selected = !passwordBasedAuth.value,
                            onClick = { passwordBasedAuth.value = false }
                        )
                        Text(text = "Disabled")
                    }

                    // Address
                    OutlinedTextField(
                        value = address.value,
                        onValueChange = { address.value = it },
                        label = { Text("Address*") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Mobile
                    OutlinedTextField(
                        value = mobile.value,
                        onValueChange = { mobile.value = it },
                        label = { Text("Mobile*") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Profile Picture
                    Text(text = "Profile Pic")
                    Button(onClick = { /* Choose profile pic logic */ }) {
                        Text(text = if (profilePicUri.value == null) "Choose File" else "File Selected")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Up Button
                    Button(
                        onClick = {
                            val user = User(
                                username = username.value,
                                email = email.value,
                                firstName = firstName.value,
                                lastName = lastName.value,
                                password = password.value,
                                confirmPassword = confirmPassword.value,
                                passwordBasedAuth = passwordBasedAuth.value,
                                address = address.value,
                                mobile = mobile.value,
                                profilePicUri = profilePicUri.value
                            )
                            onSignUpClick(user)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = "Sign Up")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun previewfun() {
    SignUpScreen({})
}

data class User(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val confirmPassword: String,
    val passwordBasedAuth: Boolean,
    val address: String,
    val mobile: String,
    val profilePicUri: Uri?
)
