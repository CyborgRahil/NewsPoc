package com.rahil.newspoc.cache.test.factory

import com.rahil.newspoc.cache.model.NewsEntity
import com.rahil.newspoc.cache.test.factory.DataFactory.Factory.randomUuid
import com.rahil.newspoc.data.model.NewsDataEntity

/**
 * Factory class for News related instances
 */
class NewsFactory {

    companion object Factory {

        fun makeCachedNews(): NewsEntity {
            val newsEntity = NewsEntity()
            newsEntity.description =  randomUuid()
            newsEntity.title =  randomUuid()
            newsEntity.urlToImage =  randomUuid()
            newsEntity.newsType =  "cnn"
            return newsEntity
        }

        fun makeNewsEntity(): NewsDataEntity {
            return NewsDataEntity(randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeNewsEntityList(count: Int): List<NewsDataEntity> {
            val newsEntities = mutableListOf<NewsDataEntity>()
            repeat(count) {
                newsEntities.add(makeNewsEntity())
            }
            return newsEntities
        }

        fun makeCachedNewsList(count: Int): List<NewsEntity> {
            val cachedNewss = mutableListOf<NewsEntity>()
            repeat(count) {
                cachedNewss.add(makeCachedNews())
            }
            return cachedNewss
        }

    }

}