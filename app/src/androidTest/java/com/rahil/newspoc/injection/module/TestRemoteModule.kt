package com.rahil.newspoc.injection.module

import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import com.rahil.newspoc.data.repository.NewsRemote
import com.rahil.newspoc.remote.NewsService
import javax.inject.Singleton

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsRemote(): NewsRemote {
        return mock()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsService(): NewsService {
        return mock()
    }
}