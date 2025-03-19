package com.salilaev.stopwatch.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.salilaev.stopwatch.ComposeFragment
import com.salilaev.stopwatch.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherScreen : ComposeFragment() {
    private val viewModel by viewModels<WeatherViewModel>()

    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.image_tashkent_night),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.7f
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Temperature: ${state.temperature}", color = Color.Black)
                Text("Weather: ${state.weatherDescription}", color = Color.Black)
                Text("Wind: ${state.wind} m/s", color = Color.Black)
                Text("Wet: ${state.wet} %", color = Color.Black)
                Button(
                    onClick = { viewModel.getCurrentWeather() }
                ) {
                    Image(
                        modifier = Modifier
                            .width(24.dp)
                            .aspectRatio(1.5f),
                        painter = painterResource(R.drawable.ic_start),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}


///Box Column Row Button Card content bar