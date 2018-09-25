package com.rahil.newspoc.cache.mapper

import com.rahil.newspoc.cache.model.NewsEntity
import com.rahil.newspoc.cache.test.factory.NewsFactory
import com.rahil.newspoc.data.model.NewsDataEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class NewsDataEntityMapperTest {

    private lateinit var newsEntityMapper: NewsEntityMapper

    @Before
    fun setUp() {
        newsEntityMapper = NewsEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val newsEntity = NewsFactory.makeNewsEntity()
        val cachedNews = newsEntityMapper.mapToCached(newsEntity)

        assertNewsDataEquality(newsEntity, cachedNews)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedNews = NewsFactory.makeCachedNews()
        val newsEntity = newsEntityMapper.mapFromCached(cachedNews)
        assertNewsDataEquality(newsEntity, cachedNews)
    }

    private fun assertNewsDataEquality(newsDataEntity: NewsDataEntity,
                                           cnnNewsEntity: NewsEntity) {
        assertEquals(newsDataEntity.description, cnnNewsEntity.description)
        assertEquals(newsDataEntity.title, cnnNewsEntity.title)
        assertEquals(newsDataEntity.urlToImage, cnnNewsEntity.urlToImage)
    }

}