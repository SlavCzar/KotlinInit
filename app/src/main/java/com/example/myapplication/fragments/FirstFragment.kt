package com.example.myapplication.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.HeadlinesAdapter
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.models.News
import com.example.myapplication.repositories.NewsRepository
import com.example.myapplication.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_first.*

private const val TAG = "FirstFragment"
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {

        d("Swiped","first Fragment")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_headlines.layoutManager = LinearLayoutManager(activity)
        if(!isAdded)
            return
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getAllHeadlines(searchQuery = "",country = "in")
        viewModel.newsList.observe(viewLifecycleOwner, Observer {
            d(TAG, "Inside observer ")
            if(it!=null && isAdded)
                recycler_headlines.adapter = HeadlinesAdapter(it)

        });
    }
}