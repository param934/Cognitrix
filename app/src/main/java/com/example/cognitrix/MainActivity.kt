package com.example.cognitrix
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.cognitrix.api.login.RetrofitInstance
import com.example.cognitrix.api.login.UserViewModel
import com.example.cognitrix.api.login.UserViewModelFactory
import com.example.cognitrix.db.AppDatabase
import com.example.cognitrix.db.UserRepository
import com.example.cognitrix.pages.LoginPage
import com.example.cognitrix.pages.SignUpScreen
import com.example.cognitrix.pages.dasboard
import com.example.cognitrix.ui.theme.CognitrixTheme


class MainActivity : ComponentActivity() {
    private lateinit var userViewModel: UserViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()
        val repository = UserRepository(RetrofitInstance.api, database.userDao())
        val viewModelFactory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        setContent {
            CognitrixTheme {
                val navController = rememberNavController()
                userViewModel.fetchUser()

                val user by userViewModel.user.collectAsState()

                val startDestination = if (user != null) "Home" else "login"

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavigationComponent(navController = navController, viewModel = userViewModel, startDestination = startDestination) // Call the navigation component
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController, viewModel: UserViewModel, startDestination: String) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginPage(onNavigateToSignUp = {
                navController.navigate("signup") // Navigate to SignUp screen
            }, viewModel = viewModel)
        }
        composable("signup") {
            SignUpScreen { data ->
                viewModel.registerUser(data)
                navController.navigate("Home")
            }
        }
        composable("Home") {
            dasboard().screen(viewModel = viewModel)

//            // Check if user session expires
//            LaunchedEffect(viewModel) {
//                while (true) {
//                    delay(10000L) // Check every 10 seconds (or adjust as needed)
//                    if (viewModel.user.value == null) {
//                        // Session expired or token revoked, navigate to login
//                        navController.navigate("login") {
//                            popUpTo("Home") { inclusive = true } // Clear back stack
//                        }
//                    }
//                }
//            }
        }
    }
}
