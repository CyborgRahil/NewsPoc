package com.rahil.newspoc.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.rahil.newspoc.data.model.NewsDataEntity

/**
 * Interface defining methods for the caching of News list. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface NewsCache {

    /**
     * Clear all news from the cache.
     */
    fun clearNewsList(): Completable

    /**
     * Save a given list of news to the cache.
     */
    fun saveNewsList(newsData: List<NewsDataEntity>): Completable

    /**
     * Retrieve a list of news, from the cache.
     */
    fun getNewsList(type:String): Flowable<List<NewsDataEntity>>

    /**
     * Check whether there is a list of news stored in the cache.
     *
     * @return true if the list is cached, otherwise false
     */
    fun isCached(newsType: String): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     *
     * @param lastCache the point in time at when the cache was last updated
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Check if the cache is expired.
     *
     * @return true if the cache is expired, otherwise false
     */
    fun isExpired(): Boolean

}