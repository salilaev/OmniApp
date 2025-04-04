package com.salilaev.omni_app.ui.news.favorites

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import com.salilaev.omni_app.ComposeFragment
import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.ui.news.formatAuthor
import com.salilaev.omni_app.ui.news.formatPublishedDateLegacy
import com.salilaev.omni_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesScreen : ComposeFragment() {

    private val viewModel: FavouritesViewModel by viewModels()

    @Composable
    override fun Content() {

        val favoritesList = viewModel.savedNews.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(8.dp)
        ) {
            if (favoritesList.value.isNotEmpty()) {
                items(favoritesList.value) { newsEntity -> NewsItem(newsEntity = newsEntity) }
            } else item {
                Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No saved news", color = Color.Black)
                }
            }
        }
    }


    @Composable
    fun NewsItem(newsEntity: NewsEntity) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    val bundle = bundleOf(
                        "title" to newsEntity.title,
                        "description" to newsEntity.description,
                        "content" to newsEntity.content,
                        "publishedAt" to newsEntity.publishedAt,
                        "author" to newsEntity.author,
                        "url" to newsEntity.url,
                        "urlToImage" to newsEntity.urlToImage
                    )
                    findNavController().navigate(
                        R.id.action_favouriteScreen_to_webViewScreen, bundle
                    )
                }, shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(4.dp)
        ) {
            AsyncImage(
                model = newsEntity.urlToImage,
                contentDescription = "Image news",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                fallback = painterResource(R.drawable.image_no_photo),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
                text = newsEntity.title ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = newsEntity.description ?: "",
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
                    text = formatAuthor(newsEntity.author), fontSize = 12.sp, color = Color.Gray
                )
                Text(
                    text = formatPublishedDateLegacy(newsEntity.publishedAt),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }


            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { viewModel.deleteNews(newsEntity.url?: "") }
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