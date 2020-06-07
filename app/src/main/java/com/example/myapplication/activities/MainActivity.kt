package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.adapters.TabLayoutPagerAdapter
import com.example.myapplication.viewmodels.NewsArticleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var articleViewModel: NewsArticleViewModel

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