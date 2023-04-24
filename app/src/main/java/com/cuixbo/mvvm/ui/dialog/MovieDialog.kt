package com.cuixbo.mvvm.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.cuixbo.mvvm.R
import com.cuixbo.mvvm.databinding.DialogMovieBinding
import com.cuixbo.mvvm.model.bean.MovieItem


class MovieDialog(val movie: MovieItem) : DialogFragment() {

    lateinit var binding: DialogMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_movie, container, false)
        binding.movie = movie
        return binding.root
    }


}