package com.rahil.newspoc.remote.test.factory

import com.rahil.newspoc.remote.NewsService
import com.rahil.newspoc.remote.model.NewsModel
import com.rahil.newspoc.remote.model.NewsSource

import com.rahil.newspoc.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for News related instances
 */
class NewsFactory {

    companion object Factory {

        fun makeNewsResponse(): NewsService.NewsApiResponse {
            val newsResponse = NewsService.NewsApiResponse()
            newsResponse.articles = makeNewsModelList(5)
            return newsResponse
        }

        fun makeNewsModelList(count: Int): List<NewsModel> {
            val newsEntities = mutableListOf<NewsModel>()
            repeat(count) {
                newsEntities.add(makeNewsModel())
            }
            return newsEntities
        }

        fun makeNewsModel(): NewsModel {
            return NewsModel(NewsSource(randomUuid(), randomUuid()), randomUuid(), randomUuid(), randomUuid(), "cnn")
        }

    }

}