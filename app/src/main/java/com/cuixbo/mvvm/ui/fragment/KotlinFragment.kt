package com.cuixbo.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.FragmentKotlinBinding

class KotlinFragment : Fragment() {


    lateinit var binding: FragmentKotlinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKotlinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = bundleOf(
            "lan" to "Kotlin",
        )
        binding.btnYes.setOnClickListener {
            findNavController().navigate(R.id.action_kotlin_to_success, args)
        }
        binding.btnNo.setOnClickListener {
//            findNavController().navigate(R.id.failed, args)
            findNavController().navigate(R.id.action_kotlin_to_failed, args)
        }
    }
}