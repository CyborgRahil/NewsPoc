package com.rahil.newspoc.test.factory

import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.presentation.model.NewsView
/**
 * Factory class for News related instances
 */
object NewsFactory {

    fun makeNewsView(): NewsView {
        return NewsView(DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid())
    }

    fun makeNewsList(count: Int): List<News> {
        val news = mutableListOf<News>()
        repeat(count) {
            news.add(NewsFactory.makeNewsModel())
        }
        return news
    }

    fun makeNewsModel(): News {
        return News(DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid())
    }

}