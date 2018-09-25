package com.rahil.newspoc.domain.test.factory

import com.rahil.newspoc.domain.model.News
import com.rahil.newspoc.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for News related instances
 */
class NewsFactory {

    companion object Factory {

        fun makeNewsList(count: Int, newsType:String): List<News> {
            val news = mutableListOf<News>()
            repeat(count) {
                news.add(makeNews(newsType))
            }
            return news
        }

        fun makeNews(newsType:String): News {
            return News(randomUuid(), randomUuid(), randomUuid(), newsType)
        }

    }

}