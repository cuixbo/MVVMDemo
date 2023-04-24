package com.cuixbo.mvvm.net.services

import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.model.bean.MovieResp
import com.cuixbo.mvvm.net.core.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DoubanService {
    @Headers("Referer: https://movie.douban.com/explore")
    @GET
    fun getData(@Url url: String): Call<ResponseBody>

    @Headers("Referer: https://movie.douban.com/explore")
    @GET
    suspend fun getMovies(@Url url: String): MovieResp
}
