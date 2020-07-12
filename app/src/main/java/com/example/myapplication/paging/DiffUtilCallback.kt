package com.example.myapplication.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.models.News

class DiffUtilCallback: DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.equals(newItem)
    }
}