package com.rahil.newspoc.injection.module

import com.nhaarman.mockito_kotlin.mock
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.rahil.newspoc.data.executor.JobExecutor
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.repository.NewsRepository
import javax.inject.Singleton

@Module
abstract class TestDataModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideNewsRepository(): NewsRepository {
            return mock()
        }

        @Provides
        @JvmStatic
        @Singleton
        fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
            return mock()
        }
    }
}
