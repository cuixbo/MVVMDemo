package com.cuixbo.mvvm.repository

import androidx.lifecycle.LiveData
import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.net.core.*
import com.cuixbo.mvvm.net.retrofit.RetrofitManager
import com.cuixbo.mvvm.net.services.WanService
import kotlinx.coroutines.flow.Flow

class ArticleRepository : BaseRepo() {

    private val wanService: WanService = RetrofitManager.create(WanService::class.java)

    suspend fun getHomeArticlesV1(page: Int): ApiResponse<ArticleResp> = fetch {
        wanService.getHomeArticle(page)
    }

    suspend fun getHomeArticlesV2(page: Int): Flow<ApiResponse<ArticleResp>> = fetchFlow {
        wanService.getHomeArticle(page)
    }

}