package com.rahil.newspoc.domain.usecase.news

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.rahil.newspoc.domain.executor.PostExecutionThread
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.interactor.news.GetBbcNews
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.domain.repository.NewsRepository
import com.rahil.newspoc.domain.test.factory.NewsFactory
import org.junit.Before
import org.junit.Test

class GetBbcNewsTest {

    private lateinit var getBbcNews: GetBbcNews

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockNewsRepository: NewsRepository
    private val BBC = "bbc-news"

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockNewsRepository = mock()
        getBbcNews = GetBbcNews(mockNewsRepository, mockThreadExecutor,
                mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getBbcNews.buildUseCaseObservable(BBC)
        verify(mockNewsRepository).getNewsList(BBC)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubNewsRepositoryGetNewss(Flowable.just(NewsFactory.makeNewsList(2,BBC)))
        val testObserver = getBbcNews.buildUseCaseObservable(BBC).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val newss = NewsFactory.makeNewsList(2,BBC)
        stubNewsRepositoryGetNewss(Flowable.just(newss))
        val testObserver = getBbcNews.buildUseCaseObservable(BBC).test()
        testObserver.assertValue(newss)
    }

    private fun stubNewsRepositoryGetNewss(single: Flowable<List<News>>) {
        whenever(mockNewsRepository.getNewsList(BBC))
                .thenReturn(single)
    }

}