package com.rahil.newspoc.data

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.rahil.newspoc.data.mapper.NewsMapper
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.repository.NewsDataStore
import com.rahil.newspoc.data.source.NewsCacheDataStore
import com.rahil.newspoc.data.source.NewsDataStoreFactory
import com.rahil.newspoc.data.source.NewsRemoteDataStore
import com.rahil.newspoc.data.test.factory.NewsFactory
import com.rahil.newspoc.domain.model.News
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsDataRepositoryTest {

    private lateinit var newsDataRepository: NewsDataRepository

    private lateinit var newsDataStoreFactory: NewsDataStoreFactory
    private lateinit var newsMapper: NewsMapper
    private lateinit var newsCacheDataStore: NewsCacheDataStore
    private lateinit var newsRemoteDataStore: NewsRemoteDataStore

    @Before
    fun setUp() {
        newsDataStoreFactory = mock()
        newsMapper = mock()
        newsCacheDataStore = mock()
        newsRemoteDataStore = mock()
        newsDataRepository = NewsDataRepository(newsDataStoreFactory, newsMapper)
        stubNewsDataStoreFactoryRetrieveCacheDataStore()
        stubNewsDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Newss">
    @Test
    fun clearNewsListCompletes() {
        stubNewsCacheClearNewss(Completable.complete())
        val testObserver = newsDataRepository.clearNewsList().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearNewsListCallsCacheDataStore() {
        stubNewsCacheClearNewss(Completable.complete())
        newsDataRepository.clearNewsList().test()
        verify(newsCacheDataStore).clearNewsList()
    }

    @Test
    fun clearNewsListNeverCallsRemoteDataStore() {
        stubNewsCacheClearNewss(Completable.complete())
        newsDataRepository.clearNewsList().test()
        verify(newsRemoteDataStore, never()).clearNewsList()
    }
    //</editor-fold>

    //<editor-fold desc="Save Newss">
    @Test
    fun saveNewsListCompletes() {
        stubNewsCacheSaveNewss(Completable.complete())
        val testObserver = newsDataRepository.saveNewsList(
                NewsFactory.makeNewsList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveNewsListCallsCacheDataStore() {
        stubNewsCacheSaveNewss(Completable.complete())
        newsDataRepository.saveNewsList(NewsFactory.makeNewsList(2)).test()
        verify(newsCacheDataStore).saveNewsList(any())
    }

    @Test
    fun saveNewsListNeverCallsRemoteDataStore() {
        stubNewsCacheSaveNewss(Completable.complete())
        newsDataRepository.saveNewsList(NewsFactory.makeNewsList(2)).test()
        verify(newsRemoteDataStore, never()).saveNewsList(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Newss">
    @Test
    fun getNewssCompletes() {
        stubNewsCacheDataStoreIsCached(Single.just(true))
        stubNewsDataStoreFactoryRetrieveDataStore(newsCacheDataStore)
        stubNewsCacheDataStoreGetNewss(Flowable.just(
                NewsFactory.makeNewsEntityList(2)))
        stubNewsCacheSaveNewss(Completable.complete())
        val testObserver = newsDataRepository.getNewsList("cnn").test()
        testObserver.assertComplete()
    }

    @Test
    fun getNewssReturnsData() {
        stubNewsCacheDataStoreIsCached(Single.just(true))
        stubNewsDataStoreFactoryRetrieveDataStore(newsCacheDataStore)
        stubNewsCacheSaveNewss(Completable.complete())
        val newss = NewsFactory.makeNewsList(2)
        val newsEntities = NewsFactory.makeNewsEntityList(2)
        newss.forEachIndexed { index, news ->
            stubNewsMapperMapFromEntity(newsEntities[index], news) }
        stubNewsCacheDataStoreGetNewss(Flowable.just(newsEntities))

        val testObserver = newsDataRepository.getNewsList("cnn").test()
        testObserver.assertValue(newss)
    }

    @Test
    fun getNewssSavesNewssWhenFromCacheDataStore() {
        stubNewsDataStoreFactoryRetrieveDataStore(newsCacheDataStore)
        stubNewsCacheSaveNewss(Completable.complete())
        newsDataRepository.saveNewsList(NewsFactory.makeNewsList(2)).test()
        verify(newsCacheDataStore).saveNewsList(any())
    }

    @Test
    fun getNewssNeverSavesNewssWhenFromRemoteDataStore() {
        stubNewsDataStoreFactoryRetrieveDataStore(newsRemoteDataStore)
        stubNewsCacheSaveNewss(Completable.complete())
        newsDataRepository.saveNewsList(NewsFactory.makeNewsList(2)).test()
        verify(newsRemoteDataStore, never()).saveNewsList(any())
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubNewsCacheSaveNewss(completable: Completable) {
        whenever(newsCacheDataStore.saveNewsList(any()))
                .thenReturn(completable)
    }

    private fun stubNewsCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(newsCacheDataStore.isCached("cnn"))
                .thenReturn(single)
    }

    private fun stubNewsCacheDataStoreGetNewss(single: Flowable<List<NewsDataEntity>>) {
        whenever(newsCacheDataStore.getNewsList("cnn"))
                .thenReturn(single)
    }

    private fun stubNewsRemoteDataStoreGetNewss(single: Flowable<List<NewsDataEntity>>) {
        whenever(newsRemoteDataStore.getNewsList("cnn"))
                .thenReturn(single)
    }

    private fun stubNewsCacheClearNewss(completable: Completable) {
        whenever(newsCacheDataStore.clearNewsList())
                .thenReturn(completable)
    }

    private fun stubNewsDataStoreFactoryRetrieveCacheDataStore() {
        whenever(newsDataStoreFactory.retrieveCacheDataStore())
                .thenReturn(newsCacheDataStore)
    }

    private fun stubNewsDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(newsDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(newsCacheDataStore)
    }

    private fun stubNewsDataStoreFactoryRetrieveDataStore(dataStore: NewsDataStore) {
        whenever(newsDataStoreFactory.retrieveDataStore(any()))
                .thenReturn(dataStore)
    }

    private fun stubNewsMapperMapFromEntity(newsDataEntity: NewsDataEntity,
                                                news: News) {
        whenever(newsMapper.mapFromEntity(newsDataEntity))
                .thenReturn(news)
    }
    //</editor-fold>

}