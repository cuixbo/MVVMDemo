package com.cuixbo.mvvm.net.services

import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.net.core.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface BaiduService {

    @GET
    fun getKeywords(@Url url: String): Call<ResponseBody>
}
