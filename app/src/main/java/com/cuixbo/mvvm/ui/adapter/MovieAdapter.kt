package com.cuixbo.mvvm.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cuixbo.mvvm.databinding.ItemMovieBinding
import com.cuixbo.mvvm.model.bean.MovieItem
import com.cuixbo.mvvm.ui.viewmodel.MovieViewModel


class MovieAdapter(
    private var dataList: MutableList<MovieItem>,
    private val movieViewModel: MovieViewModel
) : RecyclerView.Adapter<MovieAdapter.ItemHolder>() {

    init {
        setHasStableIds(true)
    }

    var listener: View.OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        println("cxb.onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<MovieItem>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        this.notifyDataSetChanged()
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val movie = dataList[position]
        holder.binding.apply {
//            MovieItem
//            word = "$position  ${dataList[position].q}"
            this.movieVM = movieViewModel
            this.movie = movie
            this.position = position
//            tvTitle.text = movie.title
//            tvDesc.text = movie.card_subtitle
//            println("cxb.${movie.card_subtitle}")
//            tvStar.text =
//                if (movie.rating.value == 0.toDouble()) "暂无评分" else "电影评分${movie.rating.value}"
//            Glide.with(this.root).load(movie.pic.large).into(ivCover)
//            ivCover.setOnClickListener(listener)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
//        return 0
        return dataList[position].id.toLong()
    }

    class ItemHolder(val binding: ItemMovieBinding) : ViewHolder(binding.root)


    fun onItemClick(view: View, word: MovieItem) {
        println("cxb.onItemClick:${word}")
    }

    companion object {
        @JvmStatic
        @BindingAdapter("onItemClick")
        fun bindOnItemClick(view: View, word: String) {
            println("cxb.bindOnItemClick:${word}")
            view.setOnClickListener {
                Toast.makeText(view.context, word, Toast.LENGTH_SHORT).show()
            }

        }


    }

}

