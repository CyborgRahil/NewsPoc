package com.rahil.newspoc

import com.rahil.newspoc.ui.mapper.NewsMapper
import com.rahil.newspoc.test.factory.NewsFactory
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
    fun mapToViewMapsData() {
        val newsView = NewsFactory.makeNewsView()
        val newsViewModel = newsMapper.mapToViewModel(newsView)

        assertEquals(newsView.description, newsViewModel.description)
        assertEquals(newsView.title, newsViewModel.title)
        assertEquals(newsView.imgUrl, newsViewModel.imgUrl)
    }

}