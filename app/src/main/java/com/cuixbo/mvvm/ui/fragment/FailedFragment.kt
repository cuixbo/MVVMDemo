package com.cuixbo.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.FragmentFailedBinding

class FailedFragment : Fragment() {

    lateinit var binding: FragmentFailedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lan = arguments?.getString("lan")
        lan?.let {
            binding.tvContent.text = "抱歉，${lan}问题回答错误"
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnBackQuestion.setOnClickListener {
            findNavController().popBackStack(R.id.question, true)
        }

        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack(R.id.home, false)
        }
    }
}