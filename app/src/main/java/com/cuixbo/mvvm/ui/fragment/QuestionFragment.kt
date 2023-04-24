package com.cuixbo.mvvm.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {
    lateinit var binding: FragmentQuestionBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("cxb.onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("cxb.onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("cxb.onCreateView")
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("cxb.onViewCreated")
        binding.btnJava.setOnClickListener {
            findNavController().navigate(R.id.java)
        }
        binding.btnKotlin.setOnClickListener {
            findNavController().navigate(R.id.kotlin)
        }

    }

    override fun onStart() {
        super.onStart()
        println("cxb.onStart")

    }

    override fun onStop() {
        super.onStop()
        println("cxb.onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("cxb.onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("cxb.onDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        println("cxb.onDetach")

    }

}