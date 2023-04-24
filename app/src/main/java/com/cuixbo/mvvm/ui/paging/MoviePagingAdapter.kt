package com.cuixbo.mvvm.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cuixbo.mvvm.databinding.ItemMovieBinding
import com.cuixbo.mvvm.model.bean.MovieItem
import com.cuixbo.mvvm.ui.adapter.MovieAdapter
import com.cuixbo.mvvm.ui.viewmodel.MovieViewModel

class MoviePagingAdapter(private val movieViewModel: MovieViewModel) :
    PagingDataAdapter<MovieItem, MoviePagingAdapter.ItemHolder>(differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        println("cxb.onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.apply {
            this.movieVM = movieViewModel
            this.movie = movie
            this.position = position
        }
    }

    fun updateItem(){

    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class ItemHolder(val binding: ItemMovieBinding) : ViewHolder(binding.root)

}