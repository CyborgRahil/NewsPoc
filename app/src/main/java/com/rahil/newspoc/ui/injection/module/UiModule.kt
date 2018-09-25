package com.rahil.newspoc.ui.injection.module

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.rahil.newspoc.domain.executor.PostExecutionThread
import com.rahil.newspoc.ui.UiThread
import com.rahil.newspoc.ui.news.NewsActivity

/**
 * Module that provides all dependencies from the mobile-ui package/layer and injects all activities.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeNewsActivity(): NewsActivity
}