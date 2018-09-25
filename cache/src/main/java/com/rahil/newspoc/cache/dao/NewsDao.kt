package com.rahil.newspoc.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rahil.newspoc.cache.model.NewsEntity

@Dao
abstract class NewsDao {

    @Query("Select * from NewsTable where newsType = :type")
    abstract fun getNews(type: String): List<NewsEntity>

    @Query("DELETE FROM NewsTable")
    abstract fun cleaNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNews(entity: NewsEntity)

    @Query("Select * from NewsTable where newsType = :newsType")
    abstract fun getNewsCount(newsType:String): List<NewsEntity>

}