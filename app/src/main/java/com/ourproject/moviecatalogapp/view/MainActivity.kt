package com.ourproject.moviecatalogapp.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ourproject.moviecatalogapp.core.data.source.remote.Resource
import com.ourproject.moviecatalogapp.ui.theme.MovieCatalogAppTheme
import com.ourproject.moviecatalogapp.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MovieCatalogAppTheme {
                Timber.d("this function is called ")
                Log.d("TAG", "onCreate: called")
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    viewModel.getMovie()
                    observeMovie()

                    Greeting("Android")
                }
            }
        }
    }

    private fun observeMovie() {
        Toast.makeText(this, "executed", Toast.LENGTH_SHORT).show()
        Timber.d("this function is called ")
        viewModel.movie.observe(this){
            when (it) {
                is Resource.Loading -> {
                    Toast.makeText(this, "executed 1", Toast.LENGTH_SHORT).show()
                    Timber.d("operate status")
                    Log.d("TAG", "onCreate: called loading")
                }

                is Resource.Success -> {
                    Log.d("TAG", "onCreate: called ${it.data}")
                    Toast.makeText(this, "executed 2", Toast.LENGTH_SHORT).show()
                    Timber.d("operate status success fetch data ${it.data}")
                }
                is Resource.Error -> {
                    Log.d("TAG", "onCreate: called ${it.message}")
                    Toast.makeText(this, "executed 3", Toast.LENGTH_SHORT).show()


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieCatalogAppTheme {
        Greeting("Android")
    }
}