package com.rahil.newspoc.ui.injection.module

import dagger.Binds
import dagger.Module
import com.rahil.newspoc.data.NewsDataRepository
import com.rahil.newspoc.data.executor.JobExecutor
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.repository.NewsRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindNewsRepository(newsDataRepository: NewsDataRepository): NewsRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}