package com.cuixbo.mvvm.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cuixbo.mvvm.databinding.ItemFooterBinding

class FooterAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFooterBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, loadState: LoadState) {
        holder.binding.apply {
            text = when (loadState) {
                is LoadState.Loading -> "加载中..."
                is LoadState.Error -> "点击重新加载"
                is LoadState.NotLoading -> "没有更多了"
            }
            if (loadState is LoadState.Error) {
                holder.binding.tvFooter.setOnClickListener { retry.invoke() }
            } else {
                holder.binding.tvFooter.setOnClickListener(null)
            }
        }
    }

    /**
     *  重写该方法，可以控制Footer的显示条件，让没有更多可以显示出来
     */
    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return super.displayLoadStateAsItem(loadState)
                || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }

    class ItemHolder(val binding: ItemFooterBinding) : RecyclerView.ViewHolder(binding.root)

}