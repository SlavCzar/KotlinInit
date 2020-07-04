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
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_third.*

class MainActivity : AppCompatActivity() {

    private lateinit var articleViewModel: NewsArticleViewModel

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                .EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) {
                showErrorLayout()
            } else {
                showNewsItems()
            }
        }
    }

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

        tab_layout_main.setupWithViewPager(viewpager_main)

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        super.onCreateOptionsMenu(menu)
//        menu?.clear()
//        menuInflater.inflate(R.menu.main_menu,menu)
//        val menuItem : MenuItem = menu!!.findItem(R.id.action_search)
//        val searchView : androidx.appcompat.widget.SearchView = menuItem.actionView as androidx.appcompat.widget.SearchView
//
//        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Log.d("Search", " : $query")
//                if(query!=null && query!="")
//                articleViewModel.getAllHeadlines(searchQuery = query)
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
////                if(newText!=null && newText!="")
////                articleViewModel.getAllHeadlines(searchQuery = newText)
//                return false
//            }
//
//        })
//
//        return true
//    }
}