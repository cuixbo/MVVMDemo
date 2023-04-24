package com.cuixbo.mvvm.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuixbo.mvvm.databinding.FragmentMovieBinding
import com.cuixbo.mvvm.datastore.DataStore.movieStore
import com.cuixbo.mvvm.model.bean.MovieItem
import com.cuixbo.mvvm.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoviePagingFragment : Fragment() {

    lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MoviePagingAdapter(viewModel)
        var pagingData: PagingData<MovieItem>? = null
        binding.rvMovies.apply {
            this.adapter = adapter.withLoadStateFooter(FooterAdapter {
                adapter.retry()
            })
            this.layoutManager = LinearLayoutManager(context)
        }

        viewModel.listener = MovieViewModel.OnMovieClick { view, movie ->
//            MovieDialog(movie).show(childFragmentManager, "movie")
            // 更新数据
            adapter.snapshot()[0]?.title = "hello"
            adapter.notifyItemChanged(0)

            // 删除数据 todo
            lifecycleScope.launch {
                pagingData = pagingData?.filter {
                    println("cxb.${it.id}")
                    it.id != "35766491"
                }
                /**
                 * 注意这种方式需要loadData()的flow需要.cachedIn(lifecycleScope)，否则报错
                 */
                adapter.submitData(pagingData!!)
            }
        }

        // 驱动PagingDataSource去加载数据
        lifecycleScope.launch {
            viewModel.loadData().collectLatest {
                println("cxb.collectLatest$it")
                pagingData = it
                adapter.submitData(it)
            }
        }

        // 监听加载状态
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                when (loadStates.refresh) {
                    is LoadState.NotLoading -> println("cxb.NotLoading")
                    is LoadState.Loading -> println("cxb.Loading")
                    is LoadState.Error -> println("cxb.Error")
                }
            }
        }

//        getMovieLiveData().observe(viewLifecycleOwner) {
//            adapter.submitData(viewLifecycleOwner.lifecycle, it)
//        }
        testDataStore()
    }

    private fun testDataStore() {
        lifecycleScope.launch {
            val movieStore = requireContext().movieStore
            val nameKey = stringPreferencesKey("name")
            val ageKey = intPreferencesKey("age")

            // 写入数据
            movieStore.edit { preference ->
                preference[nameKey] = "小黄盖"
                preference[ageKey] = 35
            }

            // 读数据
            /**
             * dataStore.data 是 Flow<Preference> 类型的flow
             * 它emit发送的是Preference（文件）
             * 从Preference中获取数据，通过Preference[Preferences.Key] 进行获取
             * flow.map() 对数据流进行转换
             * flow.first() 末端操作符，仅返回第一个值，并停止流的收集
             */
            val name = movieStore.data.first()[nameKey]
            val age = movieStore.data.map { preference ->
                println("cxb.name $preference")
                preference[ageKey]
            }.first()
            println("cxb.name ${name},${age}")
        }
    }

}
