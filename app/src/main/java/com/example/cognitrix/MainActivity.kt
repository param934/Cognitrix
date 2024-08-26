package com.example.cognitrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cognitrix.ui.theme.CognitrixTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

// Data class for the JSON response
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitrixTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginPage(
                        url = "https://jsonplaceholder.typicode.com/posts/1",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

interface ApiService {
    @GET
    fun fetchData(@Url url: String): Call<Post>
}

@Composable
fun LoginPage(url: String, modifier: Modifier = Modifier) {
    var data by remember { mutableStateOf("Loading...") }

    LaunchedEffect(url) {
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // Base URL (will be replaced by @Url in ApiService)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.fetchData(url)

        // Make network call
        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    response.body()?.let { post ->
                        data = "Title: ${post.title}\nBody: ${post.body}"
                    } ?: run {
                        data = "No data found"
                    }
                } else {
                    data = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                data = "Failed: ${t.message}"
            }
        })
    }

    // Display the fetched data
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = data)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    CognitrixTheme {
        LoginPage("https://jsonplaceholder.typicode.com/posts/1")
    }
}
