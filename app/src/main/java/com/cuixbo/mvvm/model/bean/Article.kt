package com.cuixbo.mvvm.model.bean

data class Article(
    val title: String?,
    val author: String?,
    val shareUser: String?,
    val shareDate: Long?
)

data class ArticleResp(
    val datas: List<Article>?,
    val curPage: Int?,
    val size: Int?,
    val total: Int?,
)

