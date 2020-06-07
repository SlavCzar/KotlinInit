package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemRecyclerHeadlinesBinding
import com.example.myapplication.models.News
import com.squareup.picasso.Picasso


class HeadlinesAdapter(private val newsList:List<News>) : RecyclerView.Adapter<HeadlineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_headlines,parent
            ,false)

        return HeadlineViewHolder(itemView)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.txtHeadline.text = currentItem.title
        holder.sourceName.text = currentItem.sourceName
        holder.txtPublishedAt.text = currentItem.publishedAt

        if(currentItem.imageUrl == "")
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        else
        Picasso.with(holder.itemView.context).load(currentItem.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imageView)
    }
}

class HeadlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recyclerBinding: ItemRecyclerHeadlinesBinding = ItemRecyclerHeadlinesBinding.bind(itemView)
    val imageView = recyclerBinding.imgArticleImage
    val txtHeadline = recyclerBinding.txtHeadline
    val txtPublishedAt = recyclerBinding.txtPublishedAt
    val sourceName = recyclerBinding.txtSourceName
}
