package com.cuixbo.mvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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

/**
 * 通过构造函数中的SavedStateHandle，进行LiveData的数据获取，
 * 同时保存LiveData时，要通过SavedStateHandle去保存LiveData；
 * 这样就可以实现Activity因系统原因被杀后重建恢复时，能够通过ViewModel恢复数据
 * 注意：
 * 1.SavedStateHandle使用的还是 onSaveInstanceState\onRestoreInstanceState实现的，
 * 2.它遵循Bundle传输的数据类型限制，且有传输限制大小；
 * 3.而ViewModel中变量大小是不受限制的，但它只能处理配置更改的恢复，不能处理系统内存杀死后的恢复；
 * 所以什么时候使用SavedStateHandle保存，什么时候使用ViewModel保存，是需要开发者根据实际业务自己考虑的;
 * 建议SavedStateHandle只保存关键数据，基本数据类型的；
 */
class StateChangeViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val articleRepo: ArticleRepository = ArticleRepository()

    val liveArticles: MutableLiveData<ApiResponse<ArticleResp>> = state.getLiveData("liveArticles")
    val liveCurrentIndex: MutableLiveData<Int> = state.getLiveData("liveCurrentIndex", 0)

    fun getHomeArticle() {
        viewModelScope.launch {
            val (error, resp) = articleRepo.getHomeArticlesV3(0)
            if (error == null) {
                saveArticles(resp)
                saveCurrentIndex(0)
//                liveArticles.postValue(resp)
//                liveCurrentIndex.postValue(0)
            } else {
//                liveArticles.postValue(error)
            }
        }

    }

    fun saveArticles(resp: ApiResponse<ArticleResp>?) {
        state["liveArticles"] = resp
    }

    fun saveCurrentIndex(index: Int) {
        println("cxb.saveCurrentIndex:$index")
        state["liveCurrentIndex"] = index
    }

    companion object {

    }


}