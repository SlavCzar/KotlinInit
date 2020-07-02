package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.NewsWebActivity
import com.example.myapplication.databinding.ItemRecyclerEverythingBinding
import com.example.myapplication.models.News
import com.example.myapplication.viewmodels.NewsArticleViewModel
import com.squareup.picasso.Picasso


class HeadlinesAdapter(private val viewModel: NewsArticleViewModel): PagedListAdapter<News, HeadlinesAdapter.HeadlineViewHolder>(DiffUtilCallback()) {

    private var newsList: List<News> = ArrayList()
    val drawableUnsaved: Int = R.drawable.ic_save_unmarked_foreground
    val drawableSaved: Int = R.drawable.ic_save_marked_foreground
    private lateinit var articleViewModel: NewsArticleViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recycler_everything, parent
            , false
        )
        articleViewModel = viewModel
        return HeadlineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {holder.bindTo(it)}
        holder.saveBtn.setOnClickListener {
            Log.d("Bruh", "clicked save button")
            //tried checking for the imageDrawable to get saved/unsaved status but assigning tag to imageView does the trick in getting/setting the state
            if (holder.saveBtn.tag.equals("unsaved")) {
                Log.d("adapter", "Now inserting in db")
                currentItem?.let { it1 -> articleViewModel.insertNewsInDb(it1) }
                holder.saveBtn.setImageResource(drawableSaved)
                holder.saveBtn.tag = "saved"
            } else {
                Log.d("adapter", "Now deleting from db")
                currentItem?.let { it1 -> articleViewModel.deleteNewsFromDb(it1) }
                holder.saveBtn.setImageResource(drawableUnsaved)
                holder.saveBtn.tag = "unsaved"
            }

        }
    }

    class HeadlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerBinding: ItemRecyclerEverythingBinding = ItemRecyclerEverythingBinding.bind(itemView)
        private val imageView = recyclerBinding.imgEverythingArticleImage
        private val txtHeadline = recyclerBinding.everythingTxtHeadline
        val saveBtn = recyclerBinding.saveBtn

        fun bindTo(news: News) {
            txtHeadline.text = news.title
            if (news.urlToImage == "")
                imageView.setImageResource(R.drawable.ic_launcher_foreground)
            else
                Picasso.with(itemView.context).load(news.urlToImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView)

            itemView.setOnClickListener {
                val intent = Intent(it.context, NewsWebActivity::class.java)
                intent.putExtra("url", news.url)
                val builder = CustomTabsIntent.Builder()
                val tabIntent = builder.build()
                tabIntent.launchUrl(it.context, Uri.parse(news.url))

            }

            saveBtn.tag = "unsaved"

        }
        }
}
