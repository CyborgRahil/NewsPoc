package com.rahil.newspoc.remote.mapper

import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.remote.model.NewsModel
import javax.inject.Inject

/**
 * Map a [NewsModel] to and from a [NewsDataEntity] instance when data is moving between
 * this later and the Data layer
 */
open class NewsEntityMapper @Inject constructor(): EntityMapper<NewsModel, NewsDataEntity> {

    /**
     * Map an instance of a [NewsModel] to a [NewsDataEntity] model
     */
    override fun mapFromRemote(type: NewsModel): NewsDataEntity {
        return NewsDataEntity( type.title, type.description, type.urlToImage!!,type.source.id)
    }

}