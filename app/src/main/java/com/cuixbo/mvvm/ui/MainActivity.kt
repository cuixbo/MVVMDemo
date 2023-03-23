package com.cuixbo.mvvm.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.net.core.observeState
import com.cuixbo.mvvm.ui.viewmodel.HomeViewModel


class MainActivity : AppCompatActivity() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var tvContent: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvContent = findViewById(R.id.tv_content)
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
            homeViewModel.getHomeArticle()
        }
    }


}

