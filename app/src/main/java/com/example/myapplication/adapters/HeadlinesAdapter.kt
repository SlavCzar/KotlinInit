package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.NewsDetailActivity
import com.example.myapplication.activities.NewsWebActivity
import com.example.myapplication.databinding.ItemRecyclerEverythingBinding
import com.example.myapplication.databinding.ItemRecyclerHeadlinesBinding
import com.example.myapplication.models.News
import com.example.myapplication.viewmodels.NewsArticleViewModel
import com.squareup.picasso.Picasso
import com.example.myapplication.R.drawable.ic_save_unmarked_foreground as ic_save_unmarked_foreground1


class HeadlinesAdapter(private val context: Context,val viewModel: NewsArticleViewModel) : RecyclerView.Adapter<HeadlineViewHolder>() {

    private var newsList:List<News> = ArrayList()
    val drawableUnsaved : Int = R.drawable.ic_save_unmarked_foreground
    val drawableSaved : Int = R.drawable.ic_save_marked_foreground
    private lateinit var articleViewModel: NewsArticleViewModel
    fun setNewsList(newsList: List<News>){
        Log.d("Adapter","news list changed")
        this.newsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_everything,parent
            ,false)
        articleViewModel = viewModel
        return HeadlineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        Log.d("Count", newsList.size.toString())
        return newsList.size
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.txtHeadline.text = currentItem.title

        if(currentItem.imageUrl == "")
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        else
        Picasso.with(holder.itemView.context).load(currentItem.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, NewsWebActivity::class.java)
            intent.putExtra("url",currentItem.articleUrl)
            val builder = CustomTabsIntent.Builder()
            val tabIntent = builder.build()
            tabIntent.launchUrl(it.context, Uri.parse(currentItem.articleUrl))
        }
    }
}

class HeadlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recyclerBinding: ItemRecyclerEverythingBinding = ItemRecyclerEverythingBinding.bind(itemView)
    val imageView = recyclerBinding.imgEverythingArticleImage
    val txtHeadline = recyclerBinding.everythingTxtHeadline

}
