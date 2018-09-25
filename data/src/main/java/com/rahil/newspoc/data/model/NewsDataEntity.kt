package com.rahil.newspoc.data.model

/**
 * Representation for a [NewsDataEntity] fetched from an external layer data source
 */
data class NewsDataEntity(
        val title: String,
        val description:String,
        val urlToImage: String,
        val newsType: String)