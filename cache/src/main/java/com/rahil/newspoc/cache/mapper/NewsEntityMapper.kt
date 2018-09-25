package com.rahil.newspoc.cache.mapper

import com.rahil.newspoc.cache.model.NewsEntity
import com.rahil.newspoc.data.model.NewsDataEntity
import javax.inject.Inject

/**
 * Map a [NewsEntity] instance to and from a [NewsDataEntity] instance when data is moving between
 * this later and the Data layer
 */
open class NewsEntityMapper @Inject constructor() :
        EntityMapper<NewsEntity, NewsDataEntity> {

    /**
     * Map a [NewsDataEntity] instance to a [NewsEntity] instance
     */
    override fun mapToCached(type: NewsDataEntity): NewsEntity {
        var newsEntity = NewsEntity()
        newsEntity.description = type.description
        newsEntity.title = type.title
        newsEntity.urlToImage = type.urlToImage
        newsEntity.newsType = type.newsType
        return newsEntity
    }

    /**
     * Map a [NewsEntity] instance to a [NewsDataEntity] instance
     */
    override fun mapFromCached(type: NewsEntity): NewsDataEntity {
        return NewsDataEntity( type.title, type.description, type.urlToImage, type.newsType)
    }

}