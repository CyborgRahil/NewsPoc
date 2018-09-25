package com.rahil.newspoc.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import com.rahil.newspoc.cache.db.NewsDatabase
import com.rahil.newspoc.cache.mapper.NewsEntityMapper
import com.rahil.newspoc.data.model.NewsDataEntity
import com.rahil.newspoc.data.repository.NewsCache
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving News instances. This class implements the
 * [NewsCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class NewsCacheImpl @Inject constructor(val newsDatabase: NewsDatabase,
                                        private val entityMapper: NewsEntityMapper,
                                        private val preferencesHelper: PreferencesHelper) :
        NewsCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): NewsDatabase {
        return newsDatabase
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearNewsList(): Completable {
        return Completable.defer {
            newsDatabase.cachedNewsDao().cleaNews()
            Completable.complete()
        }
    }

    /**
     * Save the given list of [NewsDataEntity] instances to the database.
     */
    override fun saveNewsList(newsData: List<NewsDataEntity>): Completable {
        return Completable.defer {
            newsData.forEach {
                newsDatabase.cachedNewsDao().insertNews(
                        entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    /**
     * Retrieve a list of [NewsDataEntity] instances from the database.
     */
    override fun getNewsList(type:String): Flowable<List<NewsDataEntity>> {
        return Flowable.defer {
            Flowable.just(newsDatabase.cachedNewsDao().getNews(type))
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Check whether there are instances are stored in the cache.
     */
    override fun isCached(newsType: String): Single<Boolean> {
        return Single.defer {
            Single.just(newsDatabase.cachedNewsDao().getNewsCount(newsType).isNotEmpty())
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time.
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}