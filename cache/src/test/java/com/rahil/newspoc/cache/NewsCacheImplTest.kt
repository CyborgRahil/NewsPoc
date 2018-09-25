package com.rahil.newspoc.cache

import android.arch.persistence.room.Room
import com.rahil.newspoc.cache.db.NewsDatabase
import com.rahil.newspoc.cache.mapper.NewsEntityMapper
import com.rahil.newspoc.cache.model.NewsEntity
import com.rahil.newspoc.cache.test.factory.NewsFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class NewsCacheImplTest {

    private var newsDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application,
            NewsDatabase::class.java).allowMainThreadQueries().build()
    private var entityMapper = NewsEntityMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)


    private val databaseHelper = NewsCacheImpl(newsDatabase,
            entityMapper, preferencesHelper)

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearNewsList().test()
        testObserver.assertComplete()
    }

    //<editor-fold desc="Save news">
    @Test
    fun saveNewsListCompletes() {
        val newsEntities = NewsFactory.makeNewsEntityList(2)

        val testObserver = databaseHelper.saveNewsList(newsEntities).test()
        testObserver.assertComplete()
    }
    
    @Test
    fun getNewsCompletes() {
        val testObserver = databaseHelper.getNewsList("cnn").test()
        testObserver.assertComplete()
    }

    @Test
    fun getNewsReturnsData() {
        val newsEntities = NewsFactory.makeNewsEntityList(2)
        val cachednews = mutableListOf<NewsEntity>()
        newsEntities.forEach {
            cachednews.add(entityMapper.mapToCached(it))
        }
        insertNewss(cachednews)

        val testObserver = databaseHelper.getNewsList("cnn").test()
        //  testObserver.assertValue(newsEntities)
    }
    //</editor-fold>

    private fun insertNewss(cnnNewsEntities: List<NewsEntity>) {
        cnnNewsEntities.forEach {
            newsDatabase.cachedNewsDao().insertNews(it)
        }
    }
}