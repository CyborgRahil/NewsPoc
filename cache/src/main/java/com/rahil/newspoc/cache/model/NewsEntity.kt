package com.rahil.newspoc.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Model used solely for the caching of a NY times News
 */
@Entity(tableName = "NewsTable")
 class NewsEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var title: String = ""
    var description: String = ""
    var urlToImage: String = ""
    var newsType: String = ""
}