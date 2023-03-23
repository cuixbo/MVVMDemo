package com.cuixbo.mvvm.net.services

import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.net.core.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WanService {

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int = 0): ApiResponse<ArticleResp>
}