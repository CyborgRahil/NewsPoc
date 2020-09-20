package com.rahil.newspoc.injection.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import com.rahil.newspoc.domain.executor.PostExecutionThread
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.repository.NewsRepository
import com.rahil.newspoc.injection.module.TestApplicationModule
import com.rahil.newspoc.injection.module.TestCacheModule
import com.rahil.newspoc.injection.module.TestDataModule
import com.rahil.newspoc.injection.module.TestRemoteModule
import com.rahil.newspoc.ui.injection.ApplicationComponent
import com.rahil.newspoc.ui.injection.module.*
import com.rahil.newspoc.test.TestApplication
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        TestApplicationModule::class,
        AndroidSupportInjectionModule::class,
        TestCacheModule::class,
        TestRemoteModule::class,
        TestDataModule::class,
        PresentationModule::class,
        UiModule::class))
interface TestApplicationComponent : ApplicationComponent {

    fun newsRepository(): NewsRepository

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

}