package com.cuixbo.mvvm.repository

import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.net.core.ApiResponse
import com.cuixbo.mvvm.net.core.BaseRepo
import com.cuixbo.mvvm.net.core.FailureResponse
import com.cuixbo.mvvm.net.core.SuccessResponse
import com.cuixbo.mvvm.net.retrofit.RetrofitManager
import com.cuixbo.mvvm.net.services.WanService

class ArticleRepository : BaseRepo() {

    private val wanService: WanService = RetrofitManager.create(WanService::class.java)

    suspend fun getHomeArticlesNew(page: Int): ApiResponse<ArticleResp> = fetch {
        wanService.getHomeArticle(page)
    }

}