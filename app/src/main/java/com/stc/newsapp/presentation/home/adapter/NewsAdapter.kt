package com.stc.newsapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stc.newsapp.databinding.NewsItemBinding
import com.stc.newsapp.domain.entity.NewsResponse

class NewsAdapter(private val itemSelected: ItemSelected) :
    ListAdapter<NewsResponse, NewsAdapter.ViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemSelected)
    }

    class ViewHolder(private val itemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(news: NewsResponse, listener: ItemSelected) {
            itemBinding.newsItemNameTv.text = news.title
            itemBinding.newsItemLayout.setOnClickListener { listener.itemSelected(news) }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<NewsResponse>() {
        override fun areItemsTheSame(
            oldItem: NewsResponse,
            newItem: NewsResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewsResponse,
            newItem: NewsResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemSelected {
        fun itemSelected(item: NewsResponse?)
    }
}