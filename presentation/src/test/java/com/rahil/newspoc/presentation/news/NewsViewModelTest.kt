package com.rahil.newspoc.presentation.news

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.rahil.newspoc.domain.interactor.news.GetBbcNews
import io.reactivex.subscribers.DisposableSubscriber
import com.rahil.newspoc.domain.interactor.news.GetCnnNews
import com.rahil.newspoc.domain.interactor.news.GetNyTimeNews
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.presentation.data.ResourceState
import com.rahil.newspoc.presentation.mapper.NewsMapper
import com.rahil.newspoc.presentation.model.NewsView
import com.rahil.newspoc.presentation.test.factory.NewsFactory
import com.rahil.newspoc.presentation.test.factory.DataFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class NewsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val NYTIMES = "the-new-york-times"
    val CNN = "cnn"
    val BBC = "bbc-news"

    lateinit var getCnnNews: GetCnnNews
    lateinit var getBbcNews: GetBbcNews
    lateinit var getNyTimesNews: GetNyTimeNews
    lateinit var newsMapper: NewsMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<News>>>

    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor()
        getCnnNews = mock()
        getNyTimesNews = mock()
        getBbcNews = mock()
        newsMapper = mock()
        newsViewModel = NewsViewModel(getCnnNews, getBbcNews, getNyTimesNews, newsMapper)
    }

    @Test
    fun getNewsExecutesUseCase() {
        newsViewModel.fetchNews(CNN)

        verify(getCnnNews, times(1)).execute(any(), anyOrNull())
        newsViewModel.fetchNews(BBC)

        verify(getCnnNews, times(1)).execute(any(), anyOrNull())
        newsViewModel.fetchNews(NYTIMES)

        verify(getCnnNews, times(1)).execute(any(), anyOrNull())
    }

    //<editor-fold desc="Success">
    @Test
    fun getCnnNewsReturnsSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)
        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(CNN)
        verify(getCnnNews).execute(captor.capture(), eq(CNN))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.status == ResourceState.SUCCESS)
    }

    @Test
    fun getBbcNewsReturnsSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)
        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(BBC)
        verify(getBbcNews).execute(captor.capture(), eq(BBC))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.status == ResourceState.SUCCESS)
    }

    @Test
    fun getNyTimesNewsReturnsSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)
        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(NYTIMES)
        verify(getNyTimesNews).execute(captor.capture(), eq(NYTIMES))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.status == ResourceState.SUCCESS)
    }

    @Test
    fun getCnnNewsReturnsDataOnSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)

        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(CNN)

        verify(getCnnNews).execute(captor.capture(), eq(CNN))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.data == viewList)
    }

    @Test
    fun getBbcNewsReturnsDataOnSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)

        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(BBC)

        verify(getBbcNews).execute(captor.capture(), eq(BBC))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.data == viewList)
    }

    @Test
    fun getNyTimesNewsReturnsDataOnSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)

        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(NYTIMES)

        verify(getNyTimesNews).execute(captor.capture(), eq(NYTIMES))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.data == viewList)
    }

    @Test
    fun getCnnNewsReturnsNoMessageOnSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)

        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(CNN)
        verify(getCnnNews).execute(captor.capture(), eq(CNN))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.message == null)
    }


    @Test
    fun getBbcNewsReturnsNoMessageOnSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)

        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(BBC)
        verify(getBbcNews).execute(captor.capture(), eq(BBC))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.message == null)
    }


    @Test
    fun getNyTimesNewsReturnsNoMessageOnSuccess() {
        val list = NewsFactory.makeNewsList(2)
        val viewList = NewsFactory.makeNewsViewList(2)

        stubNewsMapperMapToView(viewList[0], list[0])
        stubNewsMapperMapToView(viewList[1], list[1])

        newsViewModel.fetchNews(NYTIMES)
        verify(getNyTimesNews).execute(captor.capture(), eq(NYTIMES))
        captor.firstValue.onNext(list)

        assert(newsViewModel.getNews().value!!.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getCnnNewsReturnsError() {
        newsViewModel.fetchNews(CNN)
        verify(getCnnNews).execute(captor.capture(), eq(CNN))
        captor.firstValue.onError(RuntimeException())

        assert(newsViewModel.getNews().value!!.status == ResourceState.ERROR)
    }

    @Test
    fun getBbcNewsReturnsError() {
        newsViewModel.fetchNews(BBC)
        verify(getBbcNews).execute(captor.capture(), eq(BBC))
        captor.firstValue.onError(RuntimeException())

        assert(newsViewModel.getNews().value!!.status == ResourceState.ERROR)
    }

    @Test
    fun getNyTimesNewsReturnsError() {
        newsViewModel.fetchNews(NYTIMES)
        verify(getNyTimesNews).execute(captor.capture(), eq(NYTIMES))
        captor.firstValue.onError(RuntimeException())

        assert(newsViewModel.getNews().value!!.status == ResourceState.ERROR)
    }

    @Test
    fun getCnnNewsFailsAndContainsNoData() {
        newsViewModel.fetchNews(CNN)
        verify(getCnnNews).execute(captor.capture(), eq(CNN))
        captor.firstValue.onError(RuntimeException())

        assert(newsViewModel.getNews().value!!.data == null)
    }

    @Test
    fun getBbcNewsFailsAndContainsNoData() {
        newsViewModel.fetchNews(BBC)
        verify(getBbcNews).execute(captor.capture(), eq(BBC))
        captor.firstValue.onError(RuntimeException())

        assert(newsViewModel.getNews().value!!.data == null)
    }

    @Test
    fun getNyTimesNewsFailsAndContainsNoData() {
        newsViewModel.fetchNews(NYTIMES)
        verify(getNyTimesNews).execute(captor.capture(), eq(NYTIMES))
        captor.firstValue.onError(RuntimeException())

        assert(newsViewModel.getNews().value!!.data == null)
    }

    @Test
    fun getCnnNewsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        newsViewModel.fetchNews(CNN)
        verify(getCnnNews).execute(captor.capture(), eq(CNN))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(newsViewModel.getNews().value!!.message == errorMessage)
    }

    @Test
    fun getBbcNewsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        newsViewModel.fetchNews(BBC)
        verify(getBbcNews).execute(captor.capture(), eq(BBC))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(newsViewModel.getNews().value!!.message == errorMessage)
    }

    @Test
    fun getNyTimesNewsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        newsViewModel.fetchNews(NYTIMES)
        verify(getNyTimesNews).execute(captor.capture(), eq(NYTIMES))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(newsViewModel.getNews().value!!.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getNewsReturnsLoading() {
        newsViewModel.fetchNews(CNN)

        assert(newsViewModel.getNews().value!!.status == ResourceState.LOADING)

        newsViewModel.fetchNews(BBC)

        assert(newsViewModel.getNews().value!!.status == ResourceState.LOADING)

        newsViewModel.fetchNews(NYTIMES)

        assert(newsViewModel.getNews().value!!.status == ResourceState.LOADING)
    }

    @Test
    fun getNewsContainsNoDataWhenLoading() {
        newsViewModel.fetchNews(CNN)

        assert(newsViewModel.getNews().value!!.data == null)

        newsViewModel.fetchNews(BBC)
        assert(newsViewModel.getNews().value!!.data == null)

        newsViewModel.fetchNews(NYTIMES)
        assert(newsViewModel.getNews().value!!.data == null)
    }

    @Test
    fun getNewsContainsNoMessageWhenLoading() {
        newsViewModel.fetchNews(CNN)

        assert(newsViewModel.getNews().value!!.data == null)

        newsViewModel.fetchNews(BBC)
        assert(newsViewModel.getNews().value!!.data == null)

        newsViewModel.fetchNews(NYTIMES)
        assert(newsViewModel.getNews().value!!.data == null)
    }

    private fun stubNewsMapperMapToView(newsView: NewsView,
                                        news: News) {
        whenever(newsMapper.mapToView(news))
                .thenReturn(newsView)
    }

}