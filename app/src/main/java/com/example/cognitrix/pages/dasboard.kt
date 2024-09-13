package com.example.cognitrix.pages

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.cognitrix.api.login.UserViewModel

class dasboard {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    @Composable
    fun screen(viewModel: UserViewModel){
        Scaffold {
            Text(text = "welcome ${viewModel.user.value?.username}")
        }
    }
}