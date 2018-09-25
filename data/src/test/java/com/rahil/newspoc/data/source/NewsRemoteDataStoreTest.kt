package com.rahil.newspoc.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.repository.NewsRemote
import com.rahil.newspoc.data.test.factory.NewsFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsRemoteDataStoreTest {

    private lateinit var newsRemoteDataStore: NewsRemoteDataStore

    private lateinit var newsRemote: NewsRemote

    @Before
    fun setUp() {
        newsRemote = mock()
        newsRemoteDataStore = NewsRemoteDataStore(newsRemote)
    }

    //<editor-fold desc="Clear newss">
    @Test(expected = UnsupportedOperationException::class)
    fun clearnewssThrowsException() {
        newsRemoteDataStore.clearNewsList().test()
    }
    //</editor-fold>

    //<editor-fold desc="Save newss">
    @Test(expected = UnsupportedOperationException::class)
    fun savenewssThrowsException() {
        newsRemoteDataStore.saveNewsList(NewsFactory.makeNewsEntityList(2)).test()
    }
    //</editor-fold>

    //<editor-fold desc="Get newss">
    @Test
    fun getnewssCompletes() {
        stubnewsCacheGetnewss(Flowable.just(NewsFactory.makeNewsEntityList(2)))
        val testObserver = newsRemote.getNews("cnn").test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubnewsCacheGetnewss(single: Flowable<List<NewsDataEntity>>) {
        whenever(newsRemote.getNews("cnn"))
                .thenReturn(single)
    }
    //</editor-fold>

}