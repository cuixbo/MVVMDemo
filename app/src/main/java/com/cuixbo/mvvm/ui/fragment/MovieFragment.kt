package com.cuixbo.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.FragmentJavaBinding
import com.cuixbo.mvvm.databinding.FragmentMovieBinding
import com.cuixbo.mvvm.model.bean.MovieItem
import com.cuixbo.mvvm.ui.adapter.MovieAdapter
import com.cuixbo.mvvm.ui.dialog.MovieDialog
import com.cuixbo.mvvm.ui.viewmodel.MovieViewModel

class MovieFragment : Fragment() {

    lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("cxb.savedInstanceState${savedInstanceState}")
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("cxb.savedInstanceState${savedInstanceState}")
        if (savedInstanceState != null) {
            return
        }
        viewModel.getMovies()
        val adapter = MovieAdapter(mutableListOf(), viewModel)
        binding.rvMovies.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }
        adapter.setOnClickListener {
            findNavController().navigate(R.id.question)
        }
        println("cxb.viewModel.liveMovies${viewModel.liveMovies.hashCode()}")
        println("cxb.viewModel.liveMovies.hasObservers()${viewModel.liveMovies.hasObservers()}")
        viewModel.liveMovies.observe(viewLifecycleOwner) {
            println("cxb.adapter.setData(it.items)")
            adapter.setData(it.items)
        }
        viewModel.listener = MovieViewModel.OnMovieClick { view, movie ->
            MovieDialog(movie).show(childFragmentManager, "movie")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }
}