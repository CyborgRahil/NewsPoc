package com.rahil.newspoc.remote

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.remote.mapper.NewsEntityMapper
import com.rahil.newspoc.remote.test.factory.NewsFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsRemoteImplTest {

    private lateinit var entityMapper: NewsEntityMapper
    private lateinit var newsService: NewsService

    private lateinit var newsRemoteImpl: NewsRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        newsService = mock()
        newsRemoteImpl = NewsRemoteImpl(newsService, entityMapper)
    }

    //<editor-fold desc="Get Newss">
    @Test
    fun getNewsCompletes() {
        stubNewsServiceGetNewss(Flowable.just(NewsFactory.makeNewsResponse()))
        val testObserver = newsRemoteImpl.getNews("cnn").test()
        testObserver.assertComplete()
    }

    @Test
    fun getNewsReturnsData() {
        val newsResponse = NewsFactory.makeNewsResponse()
        stubNewsServiceGetNewss(Flowable.just(newsResponse))
        val newsEntities = mutableListOf<NewsDataEntity>()
        newsResponse.articles.forEach {
            newsEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = newsRemoteImpl.getNews("cnn").test()
        testObserver.assertValue(newsEntities)
    }
    //</editor-fold>

    private fun stubNewsServiceGetNewss(observable:
                                                Flowable<NewsService.NewsApiResponse>) {
        whenever(newsService.getNews("cnn", "11df8b9ece1648f3868747eb275a9900"))
                .thenReturn(observable)
    }
}