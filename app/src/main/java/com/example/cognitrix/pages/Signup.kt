import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cognitrix.api.login.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(viewModel: UserViewModel, navController: NavController) {
    Scaffold(topBar = {}) {
        var fullName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var discordId by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var isStudent by remember { mutableStateOf(true) } // Toggle state for Student/Professor

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Toggle for Student and Professor
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Student Section
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .padding(vertical = 16.dp)
                        .clickable { isStudent = true }
                ) {
                    Text(
                        text = "Student",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isStudent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(if (isStudent) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .align(Alignment.BottomCenter)
                    )
                }

                // Professor Section
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .padding(vertical = 16.dp)
                        .clickable { isStudent = false }
                ) {
                    Text(
                        text = "Professor",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!isStudent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(if (!isStudent) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .align(Alignment.BottomCenter)
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Full Name Input
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 20.sp)) {
                                append("Full Name")
                            }
                            withStyle(style = SpanStyle(color = Color.Red, fontSize = 20.sp)) {
                                append("*")
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Start)
                    )
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholder = { Text("Enter Full Name") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.AccountCircle , contentDescription = "Full Name")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )

                    // Email Input
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 20.sp)) {
                                append("Email")
                            }
                            withStyle(style = SpanStyle(color = Color.Red, fontSize = 20.sp)) {
                                append("*")
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Start)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Enter Email") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )

                    // Password Input
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 20.sp)) {
                                append("Password")
                            }
                            withStyle(style = SpanStyle(color = Color.Red, fontSize = 20.sp)) {
                                append("*")
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Start)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Enter Password") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.CheckCircle else Icons.Filled.Lock,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    Text(
                        text = "Your Password must be at least 8 characters long.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 4.dp, bottom = 24.dp)
                    )

                    // Phone Number Input
                    Text(
                        text = "Phone Number",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Start)
                    )
                    //
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        placeholder = { Text("Enter Phone Number") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Call, contentDescription = "Phone Number")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )

                    // Discord ID Input
                    Text(
                        text = "Discord ID",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Start)
                    )
                    OutlinedTextField(
                        value = discordId,
                        onValueChange = { discordId = it },
                        placeholder = { Text("Enter Discord ID") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Discord ID")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Up button
                    Button(
                        onClick = { /* Handle sign up */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Sign Up")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Already registered? Please")
                        TextButton(onClick = { navController.navigate("login") }) { // Navigate to login
                            Text(
                                text = "Login",
                                color = Color.Blue
                            )
                        }
                    }
                }
            }
        }
    }
}
