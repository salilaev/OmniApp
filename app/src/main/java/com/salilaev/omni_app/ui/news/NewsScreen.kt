package com.salilaev.omni_app.ui.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.salilaev.omni_app.ComposeFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.os.bundleOf
import com.salilaev.omni_app.R
import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.utils.formatAuthor
import com.salilaev.omni_app.utils.formatPublishedDateLegacy
import com.skydoves.landscapist.glide.GlideImage

@AndroidEntryPoint
class NewsScreen : ComposeFragment() {
    private val viewModel by viewModels<NewsViewModel>()
    private val categories =
        listOf("General", "Business", "Entertainment", "Health", "Science", "Sports", "Technology")

    @Composable
    override fun Content() {
        val newsState by viewModel.newsState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getCurrentNews(viewModel.newsState.value.selectedCategory)
        }
        NewsContent(
            categories,
            newsState,
            onCategorySelected = {
                viewModel.onCategorySelected(it)
            }
        )
    }

    @Composable
    private fun NewsContent(
        categories: List<String>,
        newsState: NewsState,
        onCategorySelected: (String) -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == newsState.selectedCategory
                    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    val textColor = if (isSelected) Color.White else Color.Black
                    Text(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .background(backgroundColor)
                            .clickable {
                                onCategorySelected(category)
                            }
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        textAlign = TextAlign.Center,
                        text = category,
                        color = textColor
                    )
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                NewsList(newsState)
                FloatingActionButton(
                    onClick = { findNavController().navigate(R.id.action_newsScreen_to_favouriteScreen) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(36.dp)
                        .size(64.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bookmark_outline),
                        contentDescription = stringResource(R.string.saved),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun NewsList(newsState: NewsState) {
        PullToRefreshBox(
            isRefreshing = newsState.isLoading,
            onRefresh = { viewModel.getCurrentNews(newsState.selectedCategory) },
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(newsState.news) { newsEntity ->
                        NewsItem(newsEntity)
                    }
                }

                if (newsState.isError) {
                    Text(
                        text = newsState.errorMessage ?: stringResource(R.string.error),
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }

    @Composable
    fun NewsItem(newsEntity: NewsEntity) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
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
                    findNavController().navigate(R.id.action_newsScreen_to_webViewScreen, bundle)
                },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            GlideImage(
                imageModel = { newsEntity.urlToImage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                },

                failure = {
                    Image(
                        painter = painterResource(R.drawable.image_no_photo),
                        contentDescription = stringResource(R.string.no_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )

            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                text = newsEntity.title ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
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
                    text = formatAuthor(newsEntity.author ?: ""),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = formatPublishedDateLegacy(newsEntity.publishedAt ?: ""),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}



