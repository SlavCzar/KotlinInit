
package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.adapters.HeadlinesAdapter
import com.example.myapplication.databinding.FragmentCategoricalHeadlinesBinding
import com.example.myapplication.network.NetworkStateResource
import com.example.myapplication.viewmodels.NewsArticleViewModel
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_categorical_headlines.*

private const val TAG = "SecondFragment"
class SecondFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentCategoricalHeadlinesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var articleViewModel: NewsArticleViewModel
    private lateinit var adapter: HeadlinesAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        d("Swiped","Second Fragment")
        _binding = FragmentCategoricalHeadlinesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_categorical.setOnRefreshListener(this)
        recycler_categorical.layoutManager = LinearLayoutManager(activity)
        articleViewModel = ViewModelProvider(this).get(NewsArticleViewModel::class.java)
        adapter = activity?.let { HeadlinesAdapter(articleViewModel) }!!
        if(!isAdded)
            return

        articleViewModel.networkStateIndicator.observe(viewLifecycleOwner, Observer {networkState->
            when(networkState){
                is NetworkStateResource.Error ->{
                    swipe_refresh_categorical.isRefreshing = false
                    showErrorLayout()
                    Log.e(TAG, "ERROR: ${networkState.message}")
                }
                is NetworkStateResource.Success ->{
                    swipe_refresh_categorical.isRefreshing = false
                    showNewsItems()
                    d(TAG, "SUCCESS ")
                }
                is NetworkStateResource.Loading ->{
                    showLoadingScreen()
                    d(TAG, "LOADING: ")
                }
            }

        })

        articleViewModel.categoricalHeadlinesList.observe(viewLifecycleOwner, Observer {
            if(it!=null && isAdded)
            {
                d(TAG, "Setting adapter with categorical data")
                adapter.submitList(it)
            }
        });
        recycler_categorical.adapter = adapter
        chipGroup.setOnCheckedChangeListener(object : ChipGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
                when(checkedId){
                    R.id.chip_all -> articleViewModel.categoryLiveData.postValue("general")
                    R.id.chip_business -> articleViewModel.categoryLiveData.postValue("business")
                    R.id.chip_sports -> articleViewModel.categoryLiveData.postValue("sports")
                    R.id.chip_entertainment -> articleViewModel.categoryLiveData.postValue( "entertainment")
                    R.id.chip_science -> articleViewModel.categoryLiveData.postValue( "science")
                    else -> {
                        chip_sports.isChecked = true
                        articleViewModel.categoryLiveData.postValue( "sport")
                    }
                }
            }

        })

    }

    override fun onResume() {
        super.onResume()
        shimmer_frame_layout_categorical.startShimmer()
    }
    override fun onPause() {
        shimmer_frame_layout_categorical.stopShimmer()
        super.onPause()
    }



    private fun showLoadingScreen() {

        shimmer_frame_layout_categorical.startShimmer()
        recycler_categorical.visibility = View.INVISIBLE
        error_image_categorical.visibility = View.INVISIBLE
        error_text_categorical.visibility = View.INVISIBLE


    }

    private fun showNewsItems() {
        shimmer_frame_layout_categorical.stopShimmer()
        shimmer_frame_layout_categorical.visibility =View.INVISIBLE
        chipGroup.visibility = View.VISIBLE
        recycler_categorical.visibility = View.VISIBLE
        error_image_categorical.visibility = View.INVISIBLE
        error_text_categorical.visibility = View.INVISIBLE
    }

    private fun showErrorLayout() {

        shimmer_frame_layout_categorical.stopShimmer()
        shimmer_frame_layout_categorical.visibility =View.INVISIBLE
        chipGroup.visibility = View.INVISIBLE
        recycler_categorical.visibility = View.INVISIBLE
        error_image_categorical.visibility = View.VISIBLE
        error_text_categorical.visibility = View.VISIBLE
    }

    override fun onRefresh() {
        articleViewModel.categoricalHeadlinesList.value?.dataSource?.invalidate()
    }
}