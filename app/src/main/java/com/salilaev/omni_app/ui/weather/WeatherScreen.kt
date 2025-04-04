package com.salilaev.omni_app.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import coil3.compose.AsyncImage
import com.salilaev.omni_app.ComposeFragment
import com.salilaev.omni_app.theme.DayTheme
import com.salilaev.omni_app.theme.NightTheme
import com.salilaev.omni_app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherScreen : ComposeFragment() {
    private val viewModel by viewModels<WeatherViewModel>()

    @Composable
    override fun Content() {
        val state by viewModel.currentWeatherState.collectAsState()

        val gradientBrush = if (state.isNight) {
            Brush.verticalGradient(DayTheme)
        } else {
            Brush.verticalGradient(NightTheme)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .safeContentPadding(),
            contentAlignment = Alignment.Center
        ) {

            if (state.isLoading) {
                LoadingContent(modifier = Modifier)
            } else if (state.isError) {
                ErrorContent(
                    errorMessage = state.errorMessage.orEmpty(),
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                WeatherContent(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }

    @Composable
    private fun LoadingContent(modifier: Modifier = Modifier) {
        CircularProgressIndicator(modifier = modifier)
    }

    @Composable
    private fun ErrorContent(errorMessage: String, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
        ) {
            IconButton(
                onClick = { viewModel.getCurrentWeather() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp, top = 25.dp)
            ) {
                Image(
                    modifier = Modifier
                        .width(34.dp)
                        .aspectRatio(1.5f),
                    painter = painterResource(R.drawable.ic_reset),
                    contentDescription = "",
                )
            }

            Text(
                text = "Error: $errorMessage",
                color = Color.Red,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }
    }

    @Composable
    private fun WeatherContent(
        state: WeatherState,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { viewModel.getCurrentWeather() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Image(
                    modifier = Modifier
                        .width(34.dp)
                        .aspectRatio(1.5f),
                    painter = painterResource(R.drawable.ic_reset),
                    contentDescription = "",
                )
            }

            Text(
                text = " Tashkent",
                color = Color.White,
                fontSize = 36.sp
            )

            Text(
                text = " ${state.temperature.roundToInt()}°",
                color = Color.White,
                fontSize = 128.sp,
                fontFamily = FontFamily(
                    Font(R.font.font_weather_temp)
                )
            )

            AsyncImage(
                model = state.iconId,
                contentDescription = (""),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp),
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(state.details) { detail ->
                    WeatherDetailContent(
                        modifier = Modifier,
                        weatherDetail = detail
                    )
                }
            }
        }
    }

    @Composable
    private fun WeatherDetailContent(
        modifier: Modifier = Modifier,
        weatherDetail: WeatherDetail
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = weatherDetail.iconId),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = "${weatherDetail.title}: ${weatherDetail.subtitle}",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}




