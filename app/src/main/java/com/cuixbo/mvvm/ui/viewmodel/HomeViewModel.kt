package com.cuixbo.mvvm.ui.viewmodel

import androidx.lifecycle.*
import androidx.work.ListenableWorker.Result.Success
import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.net.core.*
import com.cuixbo.mvvm.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
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


}