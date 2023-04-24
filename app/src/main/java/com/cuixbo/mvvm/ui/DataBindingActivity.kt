package com.cuixbo.mvvm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkRequest
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.ActivityDataBindingBinding
import com.cuixbo.mvvm.model.bean.*
import com.cuixbo.mvvm.ui.adapter.MyBindingAdapter
import com.cuixbo.mvvm.ui.viewmodel.DataBindingViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataBindingActivity : AppCompatActivity() {
    lateinit var binding: ActivityDataBindingBinding

    val viewModel: DataBindingViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDataBindingBinding>(
            this,
            R.layout.activity_data_binding
        )

        binding.word = "Jetpack"

        // 像school这种普通的实体bean，它的属性变量的变化是不会触发UI的变化的，除非是该实体bean本身发生了变化
        val school = School("Kotlin", "beijing")
        // 要想实现实体bean的属性变化也能够触发UI变化，需要修改实体类让它继承自BaseObservable，
        // 并且有两种方式：@Bindable 和 ObserverField 可以实现

        // 1.User 使用 BaseObservable+ @Bindable 实现
        val user = User("Java", 20)

        // 2.User 使用 BaseObservable+ @Bindable 实现
        val student = Student("小明", 1)
        binding.user = user
        binding.student = student
//
//        binding.tvSearch.setOnClickListener {
////            binding.school = school
//            user.age = 49
//            user.notifyChange()
//        }

        binding.searchClick = View.OnClickListener {
//            user.name = Math.random().toString()
//            user.age = 2
            println("cxb.word=${binding.word.toString()}")
            viewModel.getKeywords(binding.word.toString())
        }

        binding.etKeyword.doAfterTextChanged {
//            println("cxb.doAfterTextChanged")
//            binding.word = it.toString()
//            school.name=it.toString()
//            binding.school = School(it.toString(), it.toString())
//            user.name = it.toString()
//            student.name.set(it.toString())
        }

        /**
         * 单向绑定  android:text="@{user.name}"
         *      数据变化驱动UI变化
         * 双向绑定  android:text="@={user.name}"
         *      数据变化驱动UI变化，UI变化也会驱动数据变化
         */


        /**
         * Fragment 使用DataBiding
         * Dialog 使用DataBinding
         * RecyclerView DataBiding
         * 自定义DataBinding
         */


        /**
         * BindAdapter 用于给xml中View的自定义属性添加适配处理
         *   app:isGone="@{user.age > 5}"
         */
        val list: MutableList<Word> = mutableListOf()
        val adapter = MyBindingAdapter(list)
        binding.rvKeywords.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.liveKeywords.observe(this) { it ->
            println("cxb.keywords:$it")
            println("cxb.keywords:${binding.rvKeywords.adapter as MyBindingAdapter}")
            it.g?.let { words ->
                adapter.setData(words)
            }
        }



        /**
         * DataBinding + Paging + RecyclerView 完整demo
         */


    }

}



