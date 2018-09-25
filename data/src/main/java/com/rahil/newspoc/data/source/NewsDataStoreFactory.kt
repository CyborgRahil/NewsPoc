package com.rahil.newspoc.data.source

import com.rahil.newspoc.data.repository.NewsCache
import com.rahil.newspoc.data.repository.NewsDataStore
import javax.inject.Inject

/**
 * Create an instance of a NewsDataStore
 */
open class NewsDataStoreFactory @Inject constructor(
        private val newsCache: NewsCache,
        private val newsCacheDataStore: NewsCacheDataStore,
        private val newsRemoteDataStore: NewsRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): NewsDataStore {
        if (isCached && !newsCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): NewsDataStore {
        return newsCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): NewsDataStore {
        return newsRemoteDataStore
    }

}