package com.rahil.newspoc.domain.usecase.news

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.rahil.newspoc.domain.executor.PostExecutionThread
import com.rahil.newspoc.domain.executor.ThreadExecutor
import com.rahil.newspoc.domain.interactor.news.GetCnnNews
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.domain.repository.NewsRepository
import com.rahil.newspoc.domain.test.factory.NewsFactory
import org.junit.Before
import org.junit.Test

class GetCnnNewsTest {

    private lateinit var getCnnNews: GetCnnNews

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockNewsRepository: NewsRepository
    private val CNN = "cnn"

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockNewsRepository = mock()
        getCnnNews = GetCnnNews(mockNewsRepository, mockThreadExecutor,
                mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getCnnNews.buildUseCaseObservable(CNN)
        verify(mockNewsRepository).getNewsList(CNN)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubNewsRepositoryGetNewss(Flowable.just(NewsFactory.makeNewsList(2,CNN)))
        val testObserver = getCnnNews.buildUseCaseObservable(CNN).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val newss = NewsFactory.makeNewsList(2,CNN)
        stubNewsRepositoryGetNewss(Flowable.just(newss))
        val testObserver = getCnnNews.buildUseCaseObservable(CNN).test()
        testObserver.assertValue(newss)
    }

    private fun stubNewsRepositoryGetNewss(single: Flowable<List<News>>) {
        whenever(mockNewsRepository.getNewsList(CNN))
                .thenReturn(single)
    }

}