package com.example.myapplication.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.adapters.TabLayoutPagerAdapter
import com.example.myapplication.viewmodels.NewsArticleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_categorical_headlines.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_top_headlines.*


class MainActivity : AppCompatActivity() {

    private lateinit var articleViewModel: NewsArticleViewModel

    private fun showNewsItems() {
        recycler_top_headlines.visibility = View.VISIBLE
        recycler_categorical.visibility = View.VISIBLE
        recycler_saved_news.visibility = View.VISIBLE
    }

    private fun showErrorLayout() {
        recycler_top_headlines.visibility = View.INVISIBLE
        recycler_categorical.visibility = View.INVISIBLE
        recycler_saved_news.visibility = View.INVISIBLE

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = TabLayoutPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        viewpager_main.offscreenPageLimit = 3

        tab_layout_main.setupWithViewPager(viewpager_main)

    }

}