package com.cuixbo.mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cuixbo.mvvm.databinding.ItemKeywordBinding
import com.cuixbo.mvvm.model.bean.Word

class MyBindingAdapter(
    private var dataList: MutableList<Word>
) : RecyclerView.Adapter<MyBindingAdapter.ItemHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        println("cxb.onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemKeywordBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }

    fun setData(data: List<Word>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.apply {
            word = "$position  ${dataList[position].q}"
//            holder.bind(dataList[position])
//            word ="ddd"
        }
//        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return dataList[position].q.hashCode().toLong()
    }

    class ItemHolder(val binding: ItemKeywordBinding) : ViewHolder(binding.root) {
        fun bind(data: Word) {
//            binding.tvWord.text = data.q // 不该这么使用
            binding.word = data.q // 都dataBinding了，应该这么使用
        }
    }

//    class ItemHolder(itemView: View) : ViewHolder(itemView) {
//        lateinit var binding: ItemKeywordBinding
//
//        init {
//            binding = DataBindingUtil.bind(itemView)!!
//        }
//    }

    fun onItemClick(view: View, word: Word) {
        println("cxb.onItemClick:${word}")
    }

    companion object {
        @JvmStatic
        @BindingAdapter("onItemClick")
        fun bindOnItemClick(view: View, word: String) {
            println("cxb.bindOnItemClick:${word}")
            view.setOnClickListener {
//                view.context.nav
                Toast.makeText(view.context, word, Toast.LENGTH_SHORT).show()
            }

        }
    }

}

