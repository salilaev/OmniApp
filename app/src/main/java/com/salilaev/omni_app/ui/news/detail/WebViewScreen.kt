package com.salilaev.omni_app.ui.news.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.viewModels
import com.salilaev.omni_app.ComposeFragment
import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.theme.Primary
import com.salilaev.omni_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewScreen : ComposeFragment() {

    private val viewModel by viewModels<WebViewViewModel>()

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    override fun Content() {
        val url = arguments?.getString("url") ?: ""
        viewModel.updateUrl(url)

        val isLoading by viewModel.isLoading.collectAsState()

        val savedNews by viewModel.savedNews.collectAsState()
        val isSaved = savedNews.any { it.url == url }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {

                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true
                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                    viewModel.setLoading(true)
                                }
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    viewModel.setLoading(false)
                                }
                            }
                            loadUrl(url)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            }

            FloatingActionButton(
                onClick = {
                    val newsEntity = NewsEntity(
                        title = arguments?.getString("title") ?: "",
                        description = arguments?.getString("description") ?: "",
                        content = arguments?.getString("content") ?: "",
                        publishedAt = arguments?.getString("publishedAt") ?: "",
                        author = arguments?.getString("author") ?: "",
                        url = arguments?.getString("url") ?: "",
                        urlToImage = arguments?.getString("urlToImage") ?: "",
                        category = arguments?.getString("category") ?: "",
                        saved = true
                    )
                    viewModel.toggleSavedNews(newsEntity)
                    Toast.makeText(
                        context,
                        if (isSaved) "Removed from saved" else "Saved",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                containerColor = Primary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(36.dp)
                    .size(64.dp)

            ) {
                if (isLoading) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center),color = MaterialTheme.colorScheme.onPrimary)
                }else{
                    Image(
                        painter = painterResource(
                            id = if (isSaved) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline
                        ),
                        contentDescription = if (isSaved) stringResource(R.string.saved) else stringResource(R.string.save),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}