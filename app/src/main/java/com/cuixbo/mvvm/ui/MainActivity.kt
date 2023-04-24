package com.cuixbo.mvvm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.net.core.collectState
import com.cuixbo.mvvm.net.core.observeState
import com.cuixbo.mvvm.ui.viewmodel.HomeViewModel


class MainActivity : AppCompatActivity() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var tvContent: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvContent = findViewById(R.id.tv_content)
        startActivity(Intent(this, SingleActivity::class.java))
        if(true) return
//        homeViewModel.articles.observe(this) {
//            when (it) {
//                is LoadState.Loading -> {
//                    println("Loading")
//
//                }
//                is LoadState.Success -> {
//                    println("Success")
//                    it.data.data?.datas?.forEach {
//
//                    }
//
//                }
//                is LoadState.Error -> println("Error")
//            }
//
//        }
//
//        homeViewModel.articles3.observeState(
//            this,
//            onStart = {
//
//            },
//            onSuccess = {
//
//            },
//            onEmpty = {
//
//            },
//            onFailure = {
//
//            },
//            onFinish = {
//
//            }
//        )

        // LiveData 方式
        homeViewModel.liveArticles.observeState(this) {
            onStart {
                println("onStart")
                tvContent.text = "loading"


            }
            onSuccess {
                println("onSuccess")
                tvContent.text = it.datas.toString()
            }
            onEmpty {
                println("onEmpty")
            }
            onFailure {
                println("onFailure")
                tvContent.text = it.errorMsg
            }
            onFinish {
                println("onFinish")
            }
        }


        lifecycleScope.launchWhenCreated {
            // Flow 方式
//            homeViewModel.articlesFlow.collect {
//                when {
//                    it.isStart() -> {
//                        println("isStart")
//                    }
//                    it.isEmpty() -> {
//                        println("isEmpty")
//                    }
//                    it.isFailure() -> {
//                        println("isFailure")
//                    }
//                    it.isComplete() -> {
//                        println("isComplete")
//                    }
//                }
//            }
            // Flow + DSL 方式
            homeViewModel.articlesFlow.collectState {
                onStart {
                    println("onStart")
                    tvContent.text = "loading"
                }
                onSuccess {
                    println("onSuccess")
                    tvContent.text = it.datas.toString()
                }
                onEmpty {
                    println("onEmpty")
                }
                onFailure {
                    println("onFailure")
                    tvContent.text = it.errorMsg
                }
                onFinish {
                    println("onFinish")
                }
            }
        }


//        homeViewModel.articles3.observeState(this) {
//           it.onStart{
//
//           }.onSuccess{
//
//           }.onEmpty{
//
//           }.onException{
//
//           }.onFailure{
//
//           }.onFinish{
//
//           }
//        }


        lifecycleScope.launchWhenStarted {
//            homeViewModel.getHomeArticle()
            homeViewModel.getHomeArticleZip()
        }
    }


}

