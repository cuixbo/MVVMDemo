package com.cuixbo.mvvm.ui.fragment

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Handler.Callback
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.view.Choreographer
import android.view.Choreographer.FrameCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.util.splitToIntList
import com.cuixbo.mvvm.databinding.FragmentLagBinding

class LagFragment : Fragment() {

    lateinit var binding: FragmentLagBinding

    private val frameCallback: FrameCallback by lazy {
        var lastFrameTime = 0L
        val junk = 30 * 16
        FrameCallback {
            if (lastFrameTime > 0) {
                val interval = (it - lastFrameTime) / 1000_1000L
                if (interval > junk) {
                    println("cxb.Lag.FrameCallback.${interval}")
                }
            }
//            lastFrameTime
//                .takeIf { t -> t > 0 }
//                ?.also { t ->
//                    (t - lastFrameTime) / 1000_1000L
//                }?.takeIf { interval -> interval!! > junk }
//                ?.also { interval ->
//                    println("cxb.Lag.FrameCallback.${interval}")
//                }
            lastFrameTime = it
            println("cxb.frameTimeNanos.$it")
            Choreographer.getInstance().postFrameCallback(frameCallback)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLagBinding.inflate(inflater, container, false)
        setPrinter()
        postChoreographyCallback()
        startWatchDog()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = Bundle().apply {
            putString("lan", "Java")
        }
        binding.btnYes.setOnClickListener {
            mockLag()
        }

    }

    override fun onStop() {
        super.onStop()
        Choreographer.getInstance().removeFrameCallback(frameCallback)
        isEnd = true
    }

    companion object {
        var isEnd = false
        val handler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
            }

        }
    }

    /**
     * 三种方式监测卡顿
     * 1.Looper.printer
     * 2.WatchDog线程
     * 3.Choreographer.doFrame()
     */

    /**
     * MessageQueue中Looper.loop方法中每取出一个message处理前后都会有日志进行输出
     * 可以利用这两次printer日志输出来记录是否发生了卡顿和ANR
     * 判断事件结束后时间与开始时间差是否大于阈值
     */
    private fun setPrinter() {
        var time = 0L
        val interval = 3000

        Looper.getMainLooper().setMessageLogging {
            val thisTime = System.currentTimeMillis()
            when {
                it.startsWith(">>>>> Dispatching to") -> {
                    // message消息分发执行之前
                    time = thisTime
                }
                it.startsWith("<<<<< Finished to") -> {
                    // message消息分发执行之后
                    if (thisTime - time > interval) {
                        println("cxb.Lag.Printer.${thisTime - time}")
                        showToast("Printer检测到卡顿${thisTime - time}毫秒")
                    }
                }
            }
            println("cxb.Printer.$it,$time")
        }
    }

    private fun postChoreographyCallback() {
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    private fun startWatchDog() {
        WatchDog().start()
    }

    private fun mockLag() {
        Thread.sleep(4_000)
    }

    private fun Fragment.showToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }


    class WatchDog : Thread() {
        var tick = 0
        override fun run() {
            while (!isEnd) {
                val lastTick = tick
                Message.obtain(handler) {
                    tick++
                }.apply {
                    what = 11
                    arg1 = 33

                }.also {
                    handler.sendMessageAtFrontOfQueue(it)
                }
                // 3秒后监测，tick是否增加，如果增加了说明被消费了，如果没有增加说明发生了卡顿
                Thread.sleep(3_000)
                if (tick > lastTick) {
                    // 被消费了
//                    tick=0
                } else {
                    // 没消费，卡顿发生
                    println("cxb.Lag.WatchDog.产生了卡顿")
                    // todo report
                }
                println("cxb.Lag.WatchDog.run")
            }
        }
    }


}