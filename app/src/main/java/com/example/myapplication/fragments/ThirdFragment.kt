package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.HeadlinesAdapter
import com.example.myapplication.databinding.FragmentThirdBinding
import com.example.myapplication.viewmodels.NewsArticleViewModel
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_third.*

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var articleViewModel: NewsArticleViewModel
    private lateinit var adapter: HeadlinesAdapter


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        d("Swiped","third Fragment")
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel = ViewModelProvider(this).get(NewsArticleViewModel::class.java)
        adapter = HeadlinesAdapter(articleViewModel)

        if(!isAdded)
            return

        articleViewModel.savedHeadlinesList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        recycler_saved.layoutManager = LinearLayoutManager(activity)
        recycler_saved.adapter = adapter

    }
}