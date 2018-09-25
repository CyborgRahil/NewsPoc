package com.rahil.newspoc.domain.model

/**
 * Representation for a [News] fetched from an external layer data source
 */
data class News( val title: String, val description:String,val imageUrl:String?, val newsType:String)