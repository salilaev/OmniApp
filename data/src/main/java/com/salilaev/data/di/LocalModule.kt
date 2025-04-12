package com.salilaev.data.di

import android.content.Context
import com.salilaev.data.local.room.AppDatabase
import com.salilaev.data.local.room.dao.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @[Provides Singleton]
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.getAppDatabase(context)

    @[Provides Singleton]
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao = appDatabase.getNewsDao()
}