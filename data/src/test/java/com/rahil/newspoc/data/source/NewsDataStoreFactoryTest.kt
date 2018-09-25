package com.rahil.newspoc.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import com.rahil.newspoc.data.repository.NewsCache
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsDataStoreFactoryTest {

    private lateinit var newsDataStoreFactory: NewsDataStoreFactory

    private lateinit var newsCache: NewsCache
    private lateinit var newsCacheDataStore: NewsCacheDataStore
    private lateinit var newsRemoteDataStore: NewsRemoteDataStore

    @Before
    fun setUp() {
        newsCache = mock()
        newsCacheDataStore = mock()
        newsRemoteDataStore = mock()
        newsDataStoreFactory = NewsDataStoreFactory(newsCache,
                newsCacheDataStore, newsRemoteDataStore)
    }

    //<editor-fold desc="Retrieve Data Store">
    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubNewsCacheIsCached(Single.just(false))
        val newsDataStore = newsDataStoreFactory.retrieveDataStore(false)
        assert(newsDataStore is NewsRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubNewsCacheIsCached(Single.just(true))
        stubNewsCacheIsExpired(true)
        val newsDataStore = newsDataStoreFactory.retrieveDataStore(true)
        assert(newsDataStore is NewsRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubNewsCacheIsCached(Single.just(true))
        stubNewsCacheIsExpired(false)
        val newsDataStore = newsDataStoreFactory.retrieveDataStore(true)
        assert(newsDataStore is NewsCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Remote Data Store">
    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val newsDataStore = newsDataStoreFactory.retrieveRemoteDataStore()
        assert(newsDataStore is NewsRemoteDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Cache Data Store">
    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val newsDataStore = newsDataStoreFactory.retrieveCacheDataStore()
        assert(newsDataStore is NewsCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubNewsCacheIsCached(single: Single<Boolean>) {
        whenever(newsCache.isCached("cnn"))
                .thenReturn(single)
    }

    private fun stubNewsCacheIsExpired(isExpired: Boolean) {
        whenever(newsCache.isExpired())
                .thenReturn(isExpired)
    }
    //</editor-fold>

}