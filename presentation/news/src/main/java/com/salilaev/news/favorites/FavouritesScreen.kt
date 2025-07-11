package com.salilaev.news.favorites

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.salilaev.base.ComposeFragment
import com.salilaev.domain.news.NewsData
import com.salilaev.news.R
import com.salilaev.news.utils.formatAuthor
import com.salilaev.news.utils.formatPublishedDateLegacy
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesScreen : ComposeFragment() {

    private val viewModel: FavouritesViewModel by viewModels()

    @Composable
    override fun Content() {

        val favoritesList = viewModel.savedNews.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getSavedNews()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(8.dp)
        ) {
            if (favoritesList.value.isNotEmpty()) {
                items(favoritesList.value) { news -> NewsItem(news = news) }
            } else item {
                Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.no_saved_news), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }

    @Composable
    fun NewsItem(news: NewsData) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    val bundle = bundleOf(
                        "title" to news.title,
                        "description" to news.description,
                        "content" to news.content,
                        "publishedAt" to news.publishedAt,
                        "author" to news.author,
                        "url" to news.url,
                        "urlToImage" to news.urlToImage
                    )
                    findNavController().navigate(
                        R.id.action_favouritesScreen_to_webViewScreen, bundle
                    )
                }, shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(4.dp)
        ) {
            GlideImage(
                imageModel = { news.urlToImage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                failure = {
                    Image(
                        painter = painterResource(R.drawable.image_no_photo),
                        contentDescription = stringResource(R.string.no_image),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )

            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                text = news.title ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = news.description ?: "",
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatAuthor(news.author), fontSize = 12.sp, color = Color.Gray
                )
                Text(
                    text = formatPublishedDateLegacy(news.publishedAt),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { viewModel.deleteNews(news.url?: "")
                    Toast.makeText(
                        context,
                        "Removed from saved",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
        }
    }
}