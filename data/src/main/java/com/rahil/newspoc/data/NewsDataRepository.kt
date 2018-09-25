package com.rahil.newspoc.data

import io.reactivex.Completable
import io.reactivex.Flowable
import com.rahil.newspoc.data.mapper.NewsMapper
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.source.NewsDataStoreFactory
import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.domain.repository.NewsRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [NewsRepository] interface for communicating to and from
 * data sources
 */
class NewsDataRepository @Inject constructor(private val factory: NewsDataStoreFactory,
                                             private val newsMapper: NewsMapper) :
        NewsRepository {

    override fun clearNewsList(): Completable {
        return factory.retrieveCacheDataStore().clearNewsList()
    }

    override fun saveNewsList(news: List<News>): Completable {
        val newsEntities = mutableListOf<NewsDataEntity>()
        news.map { newsEntities.add(newsMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveNewsList(newsEntities)
    }

    override fun getNewsList(newsType:String): Flowable<List<News>> {
        return factory.retrieveCacheDataStore().isCached(newsType)
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getNewsList(newsType)
                }
                .flatMap {
                    Flowable.just(it.map { newsMapper.mapFromEntity(it) })
                }
                .flatMap {
                    saveNewsList(it).toSingle { it }.toFlowable()
                }
    }

}