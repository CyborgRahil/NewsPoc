package com.rahil.newspoc.data.mapper

import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.test.factory.NewsFactory
import com.rahil.newspoc.domain.model.News
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class NewsMapperTest {

    private lateinit var newsMapper: NewsMapper

    @Before
    fun setUp() {
        newsMapper = NewsMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val newsEntity = NewsFactory.makeNewsEntity()
        val news = newsMapper.mapFromEntity(newsEntity)

        assertNewsDataEquality(newsEntity, news)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedNews = NewsFactory.makeNews()
        val newsEntity = newsMapper.mapToEntity(cachedNews)

        assertNewsDataEquality(newsEntity, cachedNews)
    }

    private fun assertNewsDataEquality(newsDataEntity: NewsDataEntity,
                                           news: News) {
        assertEquals(newsDataEntity.description, news.description)
        assertEquals(newsDataEntity.title, news.title)
        assertEquals(newsDataEntity.urlToImage, news.imageUrl)
    }

}