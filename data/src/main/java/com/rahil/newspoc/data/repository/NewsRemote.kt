package com.rahil.newspoc.data.repository

import io.reactivex.Flowable
import com.rahil.newspoc.data.model.NewsDataEntity

/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface NewsRemote {

    /**
     * Retrieve a list of newss, from the cache
     */
    fun getNews(newsType:String): Flowable<List<NewsDataEntity>>

}