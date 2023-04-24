package com.cuixbo.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cuixbo.mvvm.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment() {
    lateinit var binding: FragmentSuccessBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lan = arguments?.getString("lan")
        lan?.let {
            binding.tvContent.text = "恭喜你，${lan}问题回答正确"
        }
    }


}