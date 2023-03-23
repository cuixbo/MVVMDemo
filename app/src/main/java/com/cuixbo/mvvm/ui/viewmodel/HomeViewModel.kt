package com.cuixbo.mvvm.ui.viewmodel

import androidx.lifecycle.*
import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.net.core.*
import com.cuixbo.mvvm.repository.ArticleRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val articleRepo: ArticleRepository = ArticleRepository()

    val liveArticles: MutableLiveData<ApiResponse<ArticleResp>> = MutableLiveData()

    fun getHomeArticle() {
        viewModelScope.launch {
            // fire()函数对start，value更新，complete做了分发
//            fire(liveArticles) {
//                articleRepo.getHomeArticlesNew(0)
//            }
            liveArticles.fire {
                articleRepo.getHomeArticlesNew(0)
            }
        }
    }


}