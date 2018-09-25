package com.rahil.newspoc.presentation.test.factory

import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.presentation.model.NewsView
import com.rahil.newspoc.presentation.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for News related instances
 */
class NewsFactory {

    companion object Factory {

        fun makeNewsList(count: Int): List<News> {
            val news = mutableListOf<News>()
            repeat(count) {
                news.add(makeNewsModel())
            }
            return news
        }

        fun makeNewsModel(): News {
            return News(randomUuid(), randomUuid(), randomUuid(), "cnn")
        }

        fun makeNewsViewList(count: Int): List<NewsView> {
            val news = mutableListOf<NewsView>()
            repeat(count) {
                news.add(makeNewsView())
            }
            return news
        }

        fun makeNewsView(): NewsView {
            return NewsView(randomUuid(), randomUuid(), randomUuid())
        }

    }

}