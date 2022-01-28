package com.rahil.newspoc.test

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import io.reactivex.plugins.RxJavaPlugins
import com.squareup.rx2.idler.Rx2Idler

class TestRunner : AndroidJUnitRunner() {

    override fun onStart() {
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("IO Scheduler"))
        super.onStart()
    }

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}