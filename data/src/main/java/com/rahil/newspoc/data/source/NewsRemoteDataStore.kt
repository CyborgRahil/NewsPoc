package com.rahil.newspoc.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.repository.NewsDataStore
import com.rahil.newspoc.data.repository.NewsRemote
import javax.inject.Inject

/**
 * Implementation of the [NewsDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class NewsRemoteDataStore @Inject constructor(private val newsRemote: NewsRemote) :
        NewsDataStore {

    override fun clearNewsList(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveNewsList(newsData: List<NewsDataEntity>): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [NewsDataEntity] instances from the API
     */
    override fun getNewsList(newsType:String): Flowable<List<NewsDataEntity>> {
        return newsRemote.getNews(newsType)
    }

    override fun isCached(newsType:String): Single<Boolean> {
        throw UnsupportedOperationException()
    }

}