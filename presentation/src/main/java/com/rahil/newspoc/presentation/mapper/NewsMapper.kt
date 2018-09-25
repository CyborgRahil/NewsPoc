package com.rahil.newspoc.presentation.mapper

import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.presentation.model.NewsView
import javax.inject.Inject

/**
 * Map a [NewsView] to and from a [News] instance when data is moving between
 * this layer and the Domain layer
 */
open class NewsMapper @Inject constructor(): Mapper<NewsView, News> {

    /**
     * Map a [News] instance to a [NewsView] instance
     */
    override fun mapToView(type: News): NewsView {
        return NewsView(type.title, type.description,type.imageUrl!!)
    }


}