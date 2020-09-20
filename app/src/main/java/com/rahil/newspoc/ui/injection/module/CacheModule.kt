package com.rahil.newspoc.ui.injection.module

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.rahil.newspoc.cache.NewsCacheImpl
import com.rahil.newspoc.cache.db.NewsDatabase
import com.rahil.newspoc.data.repository.NewsCache

/**
 * Module that provides all dependencies from the cache package/layer.
 */
@Module
abstract class CacheModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideNewsDatabase(application: Application): NewsDatabase {
            return Room.databaseBuilder(
                    application.applicationContext,
                    NewsDatabase::class.java, "news.db")
                    .build()
        }
    }

    @Binds
    abstract fun bindNewsCache(newsCacheImpl: NewsCacheImpl): NewsCache
}
