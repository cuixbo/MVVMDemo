package com.cuixbo.mvvm.ui.viewmodel

import androidx.lifecycle.*
import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.model.bean.Keywords
import com.cuixbo.mvvm.net.core.ApiResponse
import com.cuixbo.mvvm.net.core.HttpError
import com.cuixbo.mvvm.repository.ArticleRepository
import com.google.gson.Gson
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataBindingViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val articleRepo: ArticleRepository = ArticleRepository()

    val liveKeywords: MutableLiveData<Keywords> = state.getLiveData("liveKeywords")

    fun getKeywords(keyword: String) {
        viewModelScope.launch {
            val call = articleRepo.getKeywords(keyword)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val resp = response.body()?.string()
                    val keywords = Gson().fromJson(resp, Keywords::class.java)
                    keywords.g?.addAll(keywords.g)
                    keywords.g?.addAll(keywords.g)
                    state["liveKeywords"] = keywords
                    println("cxb:$keywords," + Thread.currentThread().name)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    println("cxb:onFailure")
                }
            })
        }
    }

}