package com.rahil.newspoc.data.mapper

import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.domain.model.News
import javax.inject.Inject


/**
 * Map a [NewsDataEntity] to and from a [News] instance when data is moving between
 * this later and the Domain layer
 */
open class NewsMapper @Inject constructor(): Mapper<NewsDataEntity, News> {

    /**
     * Map a [NewsDataEntity] instance to a [News] instance
     */
    override fun mapFromEntity(type: NewsDataEntity): News {
        return News( type.title, type.description, type.urlToImage,type.newsType)
    }

    /**
     * Map a [News] instance to a [NewsDataEntity] instance
     */
    override fun mapToEntity(type: News): NewsDataEntity {
        return NewsDataEntity( type.title, type.description, type.imageUrl!!,type.newsType)
    }


}