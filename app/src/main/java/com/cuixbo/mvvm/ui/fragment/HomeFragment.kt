package com.cuixbo.mvvm.ui.fragment

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.cuixbo.mvvm.R

import com.cuixbo.mvvm.databinding.FragmentHomeBinding
import com.cuixbo.mvvm.model.bean.Keywords
import com.cuixbo.mvvm.repository.ArticleRepository
import com.cuixbo.mvvm.ui.DataBindingActivity
import com.cuixbo.mvvm.ui.SingleActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    var notificationId: Int = 0

    private val articleRepo: ArticleRepository = ArticleRepository()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnQuestion.setOnClickListener {
            findNavController().navigate(R.id.question)
        }


        /**
         * 显示deeplink
         */
        binding.btnNotif.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotification()
            }
        }

        /**
         * 隐式deeplink
         */
        binding.btnDeeplink.setOnClickListener {
            // adb shell am start -a android.intent.action.VIEW -d "cxb://cuixbo.com/success/Html"
            // 好像http协议头，在浏览器中不行
            val request = NavDeepLinkRequest.Builder
                .fromUri("cxb://cuixbo.com/success/Html".toUri())
                .build()
            runCatching { findNavController().navigate(request) }
        }

        binding.btnState.setOnClickListener {
            findNavController().navigate(R.id.state)
        }

        binding.btnDataBinding.setOnClickListener {
            startActivity(Intent(requireActivity(), DataBindingActivity::class.java))
        }

        binding.btnMovies.setOnClickListener {
            findNavController().navigate(R.id.movie)
        }

        binding.btnLag.setOnClickListener {
            findNavController().navigate(R.id.lag)
        }

        binding.btnSo.setOnClickListener {
            findNavController().navigate(R.id.so)
        }


    }

    // 创建显示DeepLink，Notification点击跳转
    private fun createNotificationDeepLinkPendingIntent(): PendingIntent {
        return findNavController().createDeepLink()
            .setGraph(R.navigation.nav_graph)
            .addDestination(R.id.java)
            .addDestination(R.id.success)
            .setComponentName(SingleActivity::class.java)
            .setArguments(bundleOf("lan" to "JavaScript"))
            .createPendingIntent()
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification() {
        val activity = requireActivity()
        // 显示DeepLink 会清空应用页面栈???
        // 创建Notification
        val notification = NotificationCompat.Builder(activity, activity.packageName)
            .setContentTitle("DeepLink")
            .setContentText("深层链接测试")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(createNotificationDeepLinkPendingIntent())
            .setAutoCancel(true)
            .build()
        // 创建一个Notification Channel
        val channel = NotificationChannel(
            activity.packageName,
            "MyChannel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "显式深层链接测试"
        val manager: NotificationManager? =
            activity.getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)

        // 发送Notification
        NotificationManagerCompat.from(activity)
            .notify(notificationId++, notification)
    }

}