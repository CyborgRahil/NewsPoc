package com.rahil.newspoc.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.repository.NewsCache
import com.rahil.newspoc.data.repository.NewsDataStore
import javax.inject.Inject

/**
 * Implementation of the [NewsDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class NewsCacheDataStore @Inject constructor(private val newsCache: NewsCache) :
        NewsDataStore {

    /**
     * Clear all newss from the cache
     */
    override fun clearNewsList(): Completable {
        return newsCache.clearNewsList()
    }

    /**
     * Save a given [List] of [NewsDataEntity] instances to the cache
     */
    override fun saveNewsList(newsData: List<NewsDataEntity>): Completable {
        return newsCache.saveNewsList(newsData)
                .doOnComplete {
                    newsCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    /**
     * Retrieve a list of [NewsDataEntity] instance from the cache
     */
    override fun getNewsList(newsType:String): Flowable<List<NewsDataEntity>> {
        return newsCache.getNewsList(newsType)
    }

    /**
     * Retrieve a list of [NewsDataEntity] instance from the cache
     */
    override fun isCached(newsType:String): Single<Boolean> {
        return newsCache.isCached(newsType)
    }

}