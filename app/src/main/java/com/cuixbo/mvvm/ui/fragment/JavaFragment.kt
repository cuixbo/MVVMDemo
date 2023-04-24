package com.cuixbo.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.FragmentJavaBinding

class JavaFragment : Fragment() {

    lateinit var binding: FragmentJavaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJavaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = Bundle().apply {
            putString("lan", "Java")
        }
        binding.btnYes.setOnClickListener {
            findNavController().navigate(R.id.success, args)
        }
        binding.btnNo.setOnClickListener {
            findNavController().navigate(R.id.failed, args)
        }
    }
}