package com.rahil.newspoc.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.rahil.newspoc.data.model.NewsDataEntity

/**
 * Interface defining methods for the data operations related to News List.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface NewsDataStore {

    fun clearNewsList(): Completable

    fun saveNewsList(newsData: List<NewsDataEntity>): Completable

    fun getNewsList(newsType:String): Flowable<List<NewsDataEntity>>

    fun isCached(newsType:String): Single<Boolean>

}