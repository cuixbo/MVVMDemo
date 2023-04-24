package com.cuixbo.mvvm.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ci123.ndk.MainActivity
import com.cuixbo.mvvm.databinding.FragmentSoBinding
import com.cuixbo.mvvm.ui.util.SoUtil
import java.io.File

class SoFragment : Fragment() {

    lateinit var binding: FragmentSoBinding

    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnYes.setOnClickListener {
//            copySo()
            println("cxb.${MainActivity.stringFromJNI2("Java")}")
        }
    }

    @SuppressLint("SdCardPath")
    private fun copySo() {
        try {
            val externalCacheDir = requireContext().externalCacheDir
            val cacheDir = requireContext().cacheDir
            val source = File("$externalCacheDir/libtwo-lib.so")
            val target = File("$cacheDir/libtwo-lib.so")
            source.copyTo(target)
        } catch (e: Exception) {
            println("cxb.${e.toString()}")
        }
    }


}