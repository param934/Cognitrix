package com.example.cognitrix.pages

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.cognitrix.api.login.RegisterRequest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(onSignUpClick: (RegisterRequest) -> Unit) {
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

    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }

    val usernameError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }
    val emailError = remember { mutableStateOf<String?>(null) }
    val confirmPasswordError = remember { mutableStateOf<String?>(null) }

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
                    ValidatedTextField(
                        value = username.value,
                        onValueChange = {
                            username.value = it
                            usernameError.value = if (it.length < 6) "Username must be at least 6 characters" else null
                        },
                        label = "Username",
                        isError = usernameError.value != null,
                        errorMessage = usernameError.value
                    )

                    // Email
                    ValidatedTextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                            emailError.value = if (!isValidEmail(it)) "Invalid email address" else null
                        },
                        label = "Email*",
                        isError = emailError.value != null,
                        errorMessage = emailError.value
                    )

                    // First Name
                    ValidatedTextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        label = "First Name*",
                        isError = false,
                        errorMessage = null
                    )

                    // Last Name
                    ValidatedTextField(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        label = "Last Name*",
                        isError = false,
                        errorMessage = null
                    )

                    // Password
                    ValidatedPasswordField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = "Password",
                        isPasswordVisible = passwordVisible.value,
                        onPasswordVisibilityChange = { passwordVisible.value = !passwordVisible.value }
                    )

                    // Password Confirmation
                    ValidatedPasswordField(
                        value = confirmPassword.value,
                        onValueChange = { confirmPassword.value = it },
                        label = "Confirm Password",
                        isPasswordVisible = confirmPasswordVisible.value,
                        onPasswordVisibilityChange = { confirmPasswordVisible.value = !confirmPasswordVisible.value },
                        errorMessage = confirmPasswordError.value,
                        isError = confirmPassword.value != password.value
                    )

                    // Password-based authentication
                    Column {
                        Text(text = "Password-based authentication")
                        Row(
                            verticalAlignment = Alignment.CenterVertically
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
                    }

                    // Address
                    ValidatedTextField(
                        value = address.value,
                        onValueChange = { address.value = it },
                        label = "Address*",
                        isError = false,
                        errorMessage = null
                    )

                    // Mobile
                    ValidatedTextField(
                        value = mobile.value,
                        onValueChange = { mobile.value = it },
                        label = "Mobile*",
                        isError = false,
                        errorMessage = null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                            if (password.value == confirmPassword.value) {
                                val user = RegisterRequest(
                                    username = username.value,
                                    email = email.value,
                                    first_name = firstName.value,
                                    last_name = lastName.value,
                                    password = password.value,
                                    password2 = confirmPassword.value,
                                    password_based_authentication= passwordBasedAuth.value,
                                    address = address.value,
                                    mobile = mobile.value,
//                                    profilePicUri = profilePicUri.value
                                )
                                onSignUpClick(user)
                            } else {
                                confirmPasswordError.value = "Passwords do not match"
                            }
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

@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    errorMessage: String?,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (isError && errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}

@Composable
fun ValidatedPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    errorMessage: String? = null,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(
                onClick = onPasswordVisibilityChange,
                modifier = Modifier.semantics { contentDescription = "Toggle password visibility" }
            ) {
                Icon(imageVector = icon, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (isError && errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview
@Composable

fun previewfun() {
    SignUpScreen({})
}


