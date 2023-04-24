package com.cuixbo.mvvm.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.ActivityStateChangeBinding
import com.cuixbo.mvvm.ui.viewmodel.StateChangeViewModel


class StateChangeActivity : AppCompatActivity() {

    private val viewModel: StateChangeViewModel by viewModels()
    lateinit var binding: ActivityStateChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("cxb.onCreate:${hashCode()},$savedInstanceState,${viewModel.hashCode()}")
        binding = ActivityStateChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            viewModel.getHomeArticle()
        }
        println("cxb.liveCurrentIndex:"+viewModel.liveCurrentIndex.value)
        initObservers()
    }

    private fun initObservers(){
        viewModel.liveCurrentIndex.observe(this) {
            viewModel.liveCurrentIndex.value?.let {
                binding.tvIndex.text = "第${it}题"
            }

            viewModel.liveArticles.value?.data?.datas?.let { list ->
                binding.tvContent.text = list[it].title
            }
        }

        binding.btnPre.setOnClickListener {
            viewModel.liveCurrentIndex.value?.let {
                if (it > 0) {
                    viewModel.saveCurrentIndex(it-1)
//                    viewModel.liveCurrentIndex.postValue(it - 1)
                }
            }
        }
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this,StateChangeActivity::class.java))
            viewModel.liveCurrentIndex.value?.let {
                viewModel.liveArticles.value?.data?.datas?.let { list ->
                    if (it < list.size - 1) {
                        viewModel.saveCurrentIndex(it+1)
//                        viewModel.liveCurrentIndex.postValue(it + 1)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        println("cxb.onStart:")

    }

    override fun onStop() {
        super.onStop()
        println("cxb.onStop:")

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        println("onConfigurationChanged:${hashCode()}")
        println("onConfigurationChanged:${newConfig}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("cxb.onSaveInstanceState:")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("cxb.onRestoreInstanceState:")
    }

    override fun onDestroy() {
        super.onDestroy()

        SavedStateHandle
        println("cxb.onDestroy:${hashCode()}")
    }

    /**
     * 配置更改：下面几种情况都会导致Activity重建，ViewModel可以保存配置更改前的数据
     * 1.横竖屏切
     * 2.系统语言变更
     * 3.字体变更
     * 4.主题变更
     * manifest中activity配置了 android:configChanges="orientation|screenSize" 则不会导致横竖屏重建
     */


}

