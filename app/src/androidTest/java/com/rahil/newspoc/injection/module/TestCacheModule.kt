package com.rahil.newspoc.injection.module

import android.app.Application
import androidx.room.Room
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import com.rahil.newspoc.cache.PreferencesHelper
import com.rahil.newspoc.cache.db.NewsDatabase
import com.rahil.newspoc.data.repository.NewsCache
import javax.inject.Singleton

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun provideNewsDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(
                application.applicationContext,
                NewsDatabase::class.java, "newsDb.db")
                .build()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsCache(): NewsCache {
        return mock()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun providePreferencesHelper(): PreferencesHelper {
        return mock()
    }

}