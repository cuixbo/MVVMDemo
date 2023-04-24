package com.cuixbo.mvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.net.core.ApiResponse
import com.cuixbo.mvvm.net.core.HttpError
import com.cuixbo.mvvm.repository.ArticleRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val articleRepo: ArticleRepository = ArticleRepository()

    val liveArticles: MutableLiveData<ApiResponse<ArticleResp>> = MutableLiveData()

    val articlesFlow: MutableStateFlow<ApiResponse<ArticleResp>> = MutableStateFlow(ApiResponse())

    fun getHomeArticle() {
        viewModelScope.launch {
            // fire()函数对start，value更新，complete做了分发
//            fire(liveArticles) {
//                articleRepo.getHomeArticlesNew(0)
//            }
//            liveArticles.fire {
//                articleRepo.getHomeArticlesV1(0)
//            }
//

            // 这里需要 MutableStateFlow 做中转,用于UI更新
            articleRepo.getHomeArticlesV2(0).collect {
                articlesFlow.tryEmit(it)
            }


        }

    }

    fun getHomeArticleZip() {
//        viewModelScope.launch {
//            val fetch1 = async { articleRepo.getHomeArticlesV2(0) }
//            val fetch2 = async { articleRepo.getHomeArticlesV2(1) }
//            fetch1.await().zip(fetch2.await()) { a, b ->
//                println("zip")
//                when {
//                    a is SuccessResponse && b is SuccessResponse -> {
//                        println("a.size ${a.data.datas?.size} ")
//                        println("b.size ${b.data.datas?.size} ")
//
//                        val zipDatas = a.data.datas!! + b.data.datas!!
//                        println("all success : " + zipDatas.size)
//                        zipDatas
//
//                    }
//                    a is FailureResponse || b is FailureResponse -> {
//                        println("some one failed")
//                        if (a is FailureResponse) a else b
//                    }
//                    else -> b as ApiResponse<ArticleResp>
//                }
//            }.collect {
//                println("collect " + it)
////                articlesFlow.tryEmit(it as ApiResponse<ArticleResp>)
//
//            }
//
//        }

        viewModelScope.launch {

            val job = async(SupervisorJob()) {
                println("xbc 222 $$$$$$$$$$$$$$$$$$$$$$$")
                throw NullPointerException()
            }

            try {
                println("xbc 111 $$$$$$$$$$$$$$$$$$$$$$$")

                println("xbc 333 $$$$$$$$$$$$$$$$$$$$$$$")
                job.await()
            } catch (e: Exception) {
                println("xbc catch $$$$$$$$$$$$$$$$$$$$$$$")
                e.printStackTrace()
            }

            val res = Pair<HttpError?, ArticleResp?>(null, ArticleResp(null, 1, 1, 1))
            val (a, b) = res
            if (a == null) {
                println("has result")
            } else {
                println("has error")
            }


            val (error, resp) = articleRepo.getHomeArticlesV3(0)
            if (error == null) {
//                liveArticles.postValue(error)
            } else {
                liveArticles.postValue(resp)
            }


        }

    }


}