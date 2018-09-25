package com.rahil.newspoc.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.repository.NewsCache
import com.rahil.newspoc.data.test.factory.NewsFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsCacheDataStoreTest {

    private lateinit var newsCacheDataStore: NewsCacheDataStore

    private lateinit var newsCache: NewsCache

    @Before
    fun setUp() {
        newsCache = mock()
        newsCacheDataStore = NewsCacheDataStore(newsCache)
    }

    //<editor-fold desc="Clear Newss">
    @Test
    fun clearNewsListCompletes() {
        stubNewsCacheClearNewss(Completable.complete())
        val testObserver = newsCacheDataStore.clearNewsList().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Save Newss">
    @Test
    fun saveNewsListCompletes() {
        stubNewsCacheSaveNewss(Completable.complete())
        val testObserver = newsCacheDataStore.saveNewsList(
                NewsFactory.makeNewsEntityList(2)).test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Get Newss">
    @Test
    fun getNewsListCompletes() {
        stubNewsCacheGetNewss(Flowable.just(NewsFactory.makeNewsEntityList(2)))
        val testObserver = newsCacheDataStore.getNewsList("cnn").test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubNewsCacheSaveNewss(completable: Completable) {
        whenever(newsCache.saveNewsList(any()))
                .thenReturn(completable)
    }

    private fun stubNewsCacheGetNewss(single: Flowable<List<NewsDataEntity>>) {
        whenever(newsCache.getNewsList("cnn"))
                .thenReturn(single)
    }

    private fun stubNewsCacheClearNewss(completable: Completable) {
        whenever(newsCache.clearNewsList())
                .thenReturn(completable)
    }
    //</editor-fold>

}