package com.salilaev.omni_app.app

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.crossfade
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(),SingletonImageLoader.Factory{
    override fun newImageLoader(context: PlatformContext): ImageLoader {
      return ImageLoader.Builder(context)
                .crossfade(true)
                .build()
    }
}