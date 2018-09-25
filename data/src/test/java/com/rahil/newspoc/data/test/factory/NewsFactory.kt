package com.rahil.newspoc.data.test.factory

import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.test.factory.DataFactory.Factory.randomUuid
import com.rahil.newspoc.domain.model.News

/**
 * Factory class for News related instances
 */
class NewsFactory {

    companion object Factory {

        fun makeNewsEntity(): NewsDataEntity {
            return NewsDataEntity(randomUuid(), randomUuid(), randomUuid(), "cnn")
        }

        fun makeNews(): News {
            return News(randomUuid(), randomUuid(), randomUuid(), "cnn")
        }

        fun makeNewsEntityList(count: Int): List<NewsDataEntity> {
            val newsEntities = mutableListOf<NewsDataEntity>()
            repeat(count) {
                newsEntities.add(makeNewsEntity())
            }
            return newsEntities
        }

        fun makeNewsList(count: Int): List<News> {
            val news = mutableListOf<News>()
            repeat(count) {
                news.add(makeNews())
            }
            return news
        }

    }

}