package com.rahil.newspoc.cache.dao

import androidx.room.Room
import com.rahil.newspoc.cache.db.NewsDatabase
import com.rahil.newspoc.cache.test.factory.NewsFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
open class NewsDataEntityDaoTest {

    private lateinit var newsDatabase: NewsDatabase

    @Before
    fun initDb() {
        newsDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                NewsDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        newsDatabase.close()
    }

    @Test
    fun insertNewsSavesData() {
        val cachedNews = NewsFactory.makeCachedNews()
        newsDatabase.cachedNewsDao().insertNews(cachedNews)

        val news = newsDatabase.cachedNewsDao().getNews("cnn")
        assert(news.isNotEmpty())
    }

    @Test
    fun getNewsRetrievesData() {
        val cachedNews = NewsFactory.makeCachedNewsList(5)

        cachedNews.forEach {
            newsDatabase.cachedNewsDao().insertNews(it) }

        val retrievedNews = newsDatabase.cachedNewsDao().getNews("cnn")
        assertEquals(cachedNews.size, retrievedNews.size)
    }

}