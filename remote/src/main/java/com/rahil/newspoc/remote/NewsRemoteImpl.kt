package com.rahil.newspoc.remote

import io.reactivex.Flowable
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.repository.NewsRemote
import com.rahil.newspoc.remote.mapper.NewsEntityMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving News instances. This class implements the
 * [NewsRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class NewsRemoteImpl @Inject constructor(private val newsService: NewsService,
                                         private val entityMapper: NewsEntityMapper):
        NewsRemote {

    /**
     * Retrieve a list of [NewsDataEntity] instances from the [NewsService].
     */
    override fun getNews(newsType:String): Flowable<List<NewsDataEntity>> {
        // We are putting api key here for testing purpose. Normally it is there in gradle properties
        return newsService.getNews(newsType,"11df8b9ece1648f3868747eb275a9900")
                .map { it.articles }
                .map {
                    val entities = mutableListOf<NewsDataEntity>()
                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                    entities
                }
    }

}