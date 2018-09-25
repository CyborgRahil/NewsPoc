package com.rahil.newspoc.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import com.rahil.newspoc.domain.model.News

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface NewsRepository {

    fun clearNewsList(): Completable

    fun saveNewsList(news: List<News>): Completable

    fun getNewsList(newsType:String) : Flowable<List<News>>

}