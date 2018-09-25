package com.rahil.newspoc.ui.injection.module

import com.rahil.newspoc.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.rahil.newspoc.data.repository.NewsRemote
import com.rahil.newspoc.remote.NewsRemoteImpl
import com.rahil.newspoc.remote.NewsService
import com.rahil.newspoc.remote.NewsServiceFactory

/**
 * Module that provides all dependencies from the repository package/layer.
 */
@Module
abstract class RemoteModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideNewsService(): NewsService {
            return NewsServiceFactory.makeNewsService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindNewsRemote(newsRemoteImpl: NewsRemoteImpl): NewsRemote
}