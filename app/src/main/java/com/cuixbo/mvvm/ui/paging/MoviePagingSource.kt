package com.cuixbo.mvvm.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cuixbo.mvvm.model.bean.MovieItem
import com.cuixbo.mvvm.repository.ArticleRepository

class MoviePagingSource : PagingSource<Int, MovieItem>() {
    private val articleRepo: ArticleRepository = ArticleRepository()

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            // 这里需要处理好initialLoadSize和pageSize之间的关系
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val resp = articleRepo.getMoviesNew((page - 1) * NETWORK_PAGE_SIZE, NETWORK_PAGE_SIZE)
            val repos = resp.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repos.isNotEmpty()) page + 1 else null
            println("cxb.load:$resp")
            println("cxb.load:${repos.size}, $prevKey, $nextKey,$pageSize")
            LoadResult.Page(repos, prevKey, nextKey)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE: Int = 20
    }
}