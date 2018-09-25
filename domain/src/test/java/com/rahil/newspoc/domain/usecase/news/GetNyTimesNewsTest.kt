package com.rahil.newspoc.domain.usecase.news

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.rahil.newspoc.domain.executor.PostExecutionThread
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.interactor.news.GetNyTimeNews
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.domain.repository.NewsRepository
import com.rahil.newspoc.domain.test.factory.NewsFactory
import org.junit.Before
import org.junit.Test

class GetNyTimesNewsTest {

    private lateinit var getNyTimesNews: GetNyTimeNews

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockNewsRepository: NewsRepository
    private val GETNYTIMES="the-new-york-times"

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockNewsRepository = mock()
        getNyTimesNews = GetNyTimeNews(mockNewsRepository, mockThreadExecutor,
                mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getNyTimesNews.buildUseCaseObservable(GETNYTIMES)
        verify(mockNewsRepository).getNewsList(GETNYTIMES)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubNewsRepositoryGetNewss(Flowable.just(NewsFactory.makeNewsList(2,GETNYTIMES)))
        val testObserver = getNyTimesNews.buildUseCaseObservable(GETNYTIMES).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val news = NewsFactory.makeNewsList(2,GETNYTIMES)
        stubNewsRepositoryGetNewss(Flowable.just(news))
        val testObserver = getNyTimesNews.buildUseCaseObservable(GETNYTIMES).test()
        testObserver.assertValue(news)
    }

    private fun stubNewsRepositoryGetNewss(single: Flowable<List<News>>) {
        whenever(mockNewsRepository.getNewsList(GETNYTIMES))
                .thenReturn(single)
    }

}