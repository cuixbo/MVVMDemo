package com.cuixbo.mvvm.repository

import androidx.lifecycle.LiveData
import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.model.bean.MovieResp
import com.cuixbo.mvvm.net.core.*
import com.cuixbo.mvvm.net.retrofit.RetrofitManager
import com.cuixbo.mvvm.net.services.BaiduService
import com.cuixbo.mvvm.net.services.DoubanService
import com.cuixbo.mvvm.net.services.WanService
import kotlinx.coroutines.flow.Flow

class ArticleRepository : BaseRepo() {

    private val wanService: WanService = RetrofitManager.create(WanService::class.java)
    private val baiduService: BaiduService = RetrofitManager.create(BaiduService::class.java)
    private val doubanService: DoubanService = RetrofitManager.create(DoubanService::class.java)

    suspend fun getHomeArticlesV1(page: Int): ApiResponse<ArticleResp> = fetch {
        wanService.getHomeArticle(page)
    }

    suspend fun getHomeArticlesV2(page: Int): Flow<ApiResponse<ArticleResp>> = fetchFlow {
        wanService.getHomeArticle(page)
    }

    suspend fun getHomeArticlesV3(page: Int): Pair<RequestException?, ApiResponse<ArticleResp>?> =
        asyncCall {
            wanService.getHomeArticle(page)
        }

    suspend fun getKeywords(words: String) =
        baiduService.getKeywords("https://www.baidu.com/sugrec?prod=pc&wd=$words")

    suspend fun getMovies(start: Int) =
        doubanService.getData("https://m.douban.com/rexxar/api/v2/movie/recommend?refresh=0&start=0&count=20&selected_categories=%7B%7D&uncollect=false&tags=2022")

    suspend fun getMoviesNew(start: Int, count: Int): MovieResp {
        println("cxb.getMoviesNew $start,$count")
        val url =
            "https://m.douban.com/rexxar/api/v2/movie/recommend?refresh=0&start=$start&count=$count" +
                    "&selected_categories=%7B%22%E5%9C%B0%E5%8C%BA%22:%22%E5%8D%8E%E8%AF%AD%22,%22%E7%B1%BB%E5%9E%8B%22:%22%E5%96%9C%E5%89%A7%22%7D&uncollect=false&tags=%E5%8D%8E%E8%AF%AD,2023,%E5%96%9C%E5%89%A7"
        return doubanService.getMovies(url)
    }


}