package com.rahil.newspoc.ui.mapper

import com.rahil.newspoc.ui.model.NewsViewModel
import com.rahil.newspoc.presentation.model.NewsView
import javax.inject.Inject

/**
 * Map a [NewsView] to and from a [NewsViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class NewsMapper @Inject constructor(): Mapper<NewsViewModel, NewsView> {

    /**
     * Map a [NewsView] instance to a [NewsViewModel] instance
     */
    override fun mapToViewModel(type: NewsView): NewsViewModel {
        return NewsViewModel(type.title, type.description,type.imgUrl)
    }

}