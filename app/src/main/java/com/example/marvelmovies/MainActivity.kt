package com.example.marvelmovies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.marvelmovies.data.Search
import com.example.marvelmovies.ui.theme.MarvelMoviesTheme
import com.example.marvelmovies.ui.theme.Typography
import com.example.marvelmovies.viewmodel.AvengerMoviesViewModel
import com.example.marvelmovies.viewmodel.AvengerMoviesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = ViewModelProvider(this,
                        AvengerMoviesViewModelFactory())[AvengerMoviesViewModel::class.java]
                    Greeting(
                        name = "Android",
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    viewModel: AvengerMoviesViewModel,
    modifier: Modifier = Modifier,) {
    val searchList by remember { viewModel.searchList }
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    searchList.forEach {
        Text(
            text = it.title,
            style = Typography.headlineLarge
        )
    }
}