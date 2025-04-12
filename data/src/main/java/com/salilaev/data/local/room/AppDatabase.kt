package com.salilaev.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salilaev.data.local.room.dao.NewsDao
import com.salilaev.data.local.room.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object {
        private lateinit var database: AppDatabase

        @Synchronized
        fun getAppDatabase(context: Context): AppDatabase {
            if (!Companion::database.isInitialized) {
                database =
                    Room.databaseBuilder(context, AppDatabase::class.java, "translator_master.db")
                        .build()
            }
            return database
        }
    }
}
