package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.HeadlinesAdapter
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.viewmodels.NewsArticleViewModel
import kotlinx.android.synthetic.main.fragment_first.*

private const val TAG = "FirstFragment"
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var articleViewModel: NewsArticleViewModel
    private lateinit var adapter: HeadlinesAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        d("Swiped","first Fragment")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_headlines.layoutManager = LinearLayoutManager(activity)
        articleViewModel = ViewModelProvider(this).get(NewsArticleViewModel::class.java)
        adapter = activity?.let { HeadlinesAdapter(it,articleViewModel) }!!
        if(!isAdded)
            return

        articleViewModel.getAllHeadlines(
            source = "the-times-of-india,the-hindu,bbc-news",
            category = null
        )
        articleViewModel.newsList.observe(viewLifecycleOwner, Observer {
//            d(TAG, "Inside observer ")
            if(it!=null && isAdded)
            {
//                for(x in 0..10){
//                    d(TAG, "Search result: ${it[x].title} ")
//                }
                d(TAG, "Change has been observed")
                adapter.setNewsList(it)
                adapter.notifyDataSetChanged()
            }
        });
        recycler_headlines.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.clear()
       inflater.inflate(R.menu.main_menu,menu)
        val menuItem : MenuItem = menu.findItem(R.id.action_search)
        val searchView : androidx.appcompat.widget.SearchView = menuItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("Search", " : $query")
                if(query!=null && query!="")
                    articleViewModel.getAllHeadlines(searchQuery = query, category = null)
                else
                    articleViewModel.getAllHeadlines(category = null)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText==null || newText=="")
                articleViewModel.getAllHeadlines(category = null)
                return false
            }
        })
        searchView.setOnCloseListener (object : androidx.appcompat.widget.SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                articleViewModel.getAllHeadlines(
                    source = "the-times-of-india,bbc-news,the-hindu,the-telegraph",
                    category = null
                )
                return true
            }

        } )
        super.onCreateOptionsMenu(menu, inflater)
    }


}