package com.rahil.newspoc.cache.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.rahil.newspoc.cache.dao.NewsDao
import com.rahil.newspoc.cache.model.NewsEntity
import javax.inject.Inject

@Database(entities = arrayOf(NewsEntity::class), version = 1, exportSchema = false)
abstract class NewsDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedNewsDao(): NewsDao

    private var INSTANCE: NewsDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): NewsDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            NewsDatabase::class.java, "NewsDb.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

}