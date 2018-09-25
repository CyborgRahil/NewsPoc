package com.rahil.newspoc.remote.mapper

import com.rahil.newspoc.remote.test.factory.NewsFactory
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
    fun mapFromRemoteMapsData() {
        val newsModel = NewsFactory.makeNewsModel()
        val newsEntity = newsEntityMapper.mapFromRemote(newsModel)

        assertEquals(newsModel.description, newsEntity.description)
        assertEquals(newsModel.title, newsEntity.title)
        assertEquals(newsModel.urlToImage, newsEntity.urlToImage)
    }

}