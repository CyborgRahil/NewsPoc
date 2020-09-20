package com.rahil.newspoc.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahil.newspoc.domain.interactor.news.GetBbcNews
import io.reactivex.subscribers.DisposableSubscriber
import com.rahil.newspoc.domain.interactor.news.GetCnnNews
import com.rahil.newspoc.domain.interactor.news.GetNyTimeNews
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.presentation.data.Resource
import com.rahil.newspoc.presentation.data.ResourceState
import com.rahil.newspoc.presentation.mapper.NewsMapper
import com.rahil.newspoc.presentation.model.NewsView
import com.rahil.newspoc.presentation.utility.NewsConstant
import javax.inject.Inject

open class NewsViewModel @Inject internal constructor(
        private val getCnnNews: GetCnnNews,
        private val getBbcNews: GetBbcNews,
        private val getNyTimeNews: GetNyTimeNews,
        private val newsMapper: NewsMapper) : ViewModel() {

    private val newsLiveData: MutableLiveData<Resource<List<NewsView>>> =
            MutableLiveData()

    override fun onCleared() {
        getCnnNews.dispose()
        getBbcNews.dispose()
        getNyTimeNews.dispose()
        super.onCleared()
    }

    fun getNews(): LiveData<Resource<List<NewsView>>> {
        return newsLiveData
    }

    fun fetchNews(newsConstant: String) {

            when (newsConstant) {
                NewsConstant.NYTIMES -> {
                    newsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
                    return getNyTimeNews.execute(NewsSubscriber(),NewsConstant.NYTIMES)
                }
                NewsConstant.CNN -> {
                    newsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
                    return getCnnNews.execute(NewsSubscriber(),NewsConstant.CNN)
                }
                NewsConstant.BBC -> {
                    newsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
                    return getBbcNews.execute(NewsSubscriber(), NewsConstant.BBC)
                }
            }
    }

    inner class NewsSubscriber : DisposableSubscriber<List<News>>() {

        override fun onComplete() {}

        override fun onNext(t: List<News>) {
            newsLiveData.postValue(Resource(ResourceState.SUCCESS, t.map { newsMapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            newsLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

    }
}