package com.rahil.newspoc.remote.model

/**
 * Representation for a [NewsModel] fetched from the API
 */
class NewsModel(val source: NewsSource, val title: String, val description: String, val urlToImage: String?,val content: String?)