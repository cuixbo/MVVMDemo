package com.cuixbo.mvvm.ui.viewmodel

import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.*
import androidx.paging.*
import com.cuixbo.mvvm.model.bean.ArticleResp
import com.cuixbo.mvvm.model.bean.Keywords
import com.cuixbo.mvvm.model.bean.MovieItem
import com.cuixbo.mvvm.model.bean.MovieResp
import com.cuixbo.mvvm.net.core.ApiResponse
import com.cuixbo.mvvm.net.core.HttpError
import com.cuixbo.mvvm.repository.ArticleRepository
import com.cuixbo.mvvm.ui.paging.MoviePagingSource
import com.google.gson.Gson
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val articleRepo: ArticleRepository = ArticleRepository()

    val liveMovies: MutableLiveData<MovieResp> = MutableLiveData()

    var listener: OnMovieClick? = null

    fun getMovies() {
        viewModelScope.launch {
            val call = articleRepo.getMovies(0)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val resp = response.body()?.string()
                    println("cxb:${resp}")
                    val movieResp = Gson().fromJson(resp, MovieResp::class.java)

//                    state["liveKeywords"] = keywords
                    println("cxb:${movieResp?.items},")
                    liveMovies.postValue(movieResp)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    println("cxb:onFailure")
                }
            })
        }
    }


    /**
     * initialLoadSize默认是pageSize的3倍，目的是用于初次加载能够让用户查阅到更多的信息，而不用频繁的去翻页
     * 第2次翻页请求时，才会走pageSize
     */
    fun loadData(): Flow<PagingData<MovieItem>> = Pager(
        config = PagingConfig(
            pageSize = MoviePagingSource.NETWORK_PAGE_SIZE,
            initialLoadSize = MoviePagingSource.NETWORK_PAGE_SIZE,
            prefetchDistance = 3,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = { MoviePagingSource() }
    ).flow.cachedIn(viewModelScope)

    fun getMovieLiveData(): LiveData<PagingData<MovieItem>> = Pager(
        config = PagingConfig(
            pageSize = MoviePagingSource.NETWORK_PAGE_SIZE,
            initialLoadSize = MoviePagingSource.NETWORK_PAGE_SIZE,
            prefetchDistance = 3,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = { MoviePagingSource() }
    ).liveData

    fun onMovieClick(view: View, movie: MovieItem) {
        println("onMovieClick${movie.title}")
        listener?.apply {
            onMovieClick(view, movie)
        }
    }

    fun onMovieBuyClick(view: View, movie: MovieItem) {
        println("onMovieBuyClick")
    }

    fun interface OnMovieClick {
        fun onMovieClick(view: View, movie: MovieItem)
    }


}


