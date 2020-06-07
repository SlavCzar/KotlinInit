package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.HeadlinesAdapter
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.viewmodels.NewsArticleViewModel
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_second.*

private const val TAG = "SecondFragment"
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var articleViewModel: NewsArticleViewModel
    private lateinit var adapter: HeadlinesAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        d("Swiped","Second Fragment")
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_everything.layoutManager = LinearLayoutManager(activity)
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
        chipGroup.setOnCheckedChangeListener(object : ChipGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
                when(checkedId){
                    R.id.chip_all -> articleViewModel.getAllHeadlines(category="general",country = "in")
                    R.id.chip_business -> articleViewModel.getAllHeadlines(category="business",country = "in")
                    R.id.chip_tech -> articleViewModel.getAllHeadlines(category="tech",country = "in")
                    R.id.chip_sports -> articleViewModel.getAllHeadlines(category="sports",country = "in")
                    R.id.chip_entertainment -> articleViewModel.getAllHeadlines(category="entertainment",country = "in")
                    R.id.chip_science -> articleViewModel.getAllHeadlines(category="science",country = "in")
                    else -> {
                        chip_all.isChecked = true
                        articleViewModel.getAllHeadlines(category="general")
                    }
                }
            }

        })

        recycler_everything.adapter = adapter

    }
}