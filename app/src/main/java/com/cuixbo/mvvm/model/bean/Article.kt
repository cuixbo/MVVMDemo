package com.cuixbo.mvvm.model.bean

import java.io.Serializable

data class Article (
    val title: String?,
    val author: String?,
    val shareUser: String?,
    val shareDate: Long?
): Serializable

data class ArticleResp(
    val datas: List<Article>?,
    val curPage: Int?,
    val size: Int?,
    val total: Int?,
): Serializable

