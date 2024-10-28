package com.example.cognitrix

import SignUpPage
import android.annotation.SuppressLint
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.cognitrix.api.login.RetrofitInstance
import com.example.cognitrix.api.login.UserViewModel
import com.example.cognitrix.api.login.UserViewModelFactory
import com.example.cognitrix.db.AppDatabase
import com.example.cognitrix.db.UserRepository
import com.example.cognitrix.pages.LoginPage
import com.example.cognitrix.ui.theme.CognitrixTheme
import androidx.navigation.NavController
import androidx.navigation.NavHostController


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                val navController = rememberNavController() // Initialize NavController
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(navController = navController, viewModel = userViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(viewModel = viewModel, navController = navController)
        }
        composable("signup") {
            SignUpPage(viewModel = viewModel, navController = navController)
        }
    }
}
