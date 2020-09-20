package com.rahil.newspoc.cache

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
@Singleton
open class PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private val PREF_NEWS_APP_PACKAGE_NAME = "com.rahil.newspoc.preferences"

        private val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val newsAppPref: SharedPreferences

    init {
        newsAppPref = context.getSharedPreferences(PREF_NEWS_APP_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = newsAppPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = newsAppPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

}
