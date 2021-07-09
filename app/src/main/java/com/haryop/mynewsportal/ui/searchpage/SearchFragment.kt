package com.haryop.mynewsportal.ui.searchpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.FragmentNewslistPageBinding
import com.haryop.mynewsportal.ui.ToolbarTitleListener
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.NewsListAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragmentBinding<FragmentNewslistPageBinding>(),
    NewsListAdapter.NewsListItemListener, SwipeRefreshLayout.OnRefreshListener{

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewslistPageBinding
        get() = FragmentNewslistPageBinding::inflate

    lateinit var viewbinding: FragmentNewslistPageBinding
    var query = ""
    override fun setupView(binding: FragmentNewslistPageBinding) {
        viewbinding = binding
        setUpRecyclerView()
        setupToolbar()
    }

    fun setupToolbar() {
        arguments?.getString("query")?.let {
            query = it
            val hashMap:HashMap<String,String> = HashMap<String,String>()
            hashMap.put("query", it)
            hashMap.put("page", "1")
            viewModel.start(hashMap)
            onGetSourcesObserver()
        }
    }

    private lateinit var adapter: NewsListAdapter
    fun setUpRecyclerView() = with(viewbinding) {
        adapter = NewsListAdapter(this@SearchFragment)
        adapter.setCurrent_page(adapter.SEARCH_PAGE)

        newslistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newslistRecyclerView.adapter = adapter

        newslistSwipeContainer.setOnRefreshListener(this@SearchFragment)
    }

    private val viewModel: SearchViewModel by viewModels()
    private fun onGetSourcesObserver() = with(viewbinding) {
        viewModel.getEverything.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    newslistSwipeContainer.isRefreshing = false
                    if (!it.data.isNullOrEmpty()) {
                        var items = ArrayList<Any>()
                        items.addAll(ArrayList(it.data))
                        items.add(adapter.ITEM_TYPE_BOTTOMSPACE_LAYOUT)
                        adapter.setItems(items)
                        adapter.notifyDataSetChanged()
                    } else {
                        Timber.e("getSearchEndpoint.observe: SUCCESS tapi null")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                Resource.Status.ERROR -> {
                    newslistSwipeContainer.isRefreshing = false
                    Timber.e("getSearchEndpoint.observe: error")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Timber.e("getSearchEndpoint.observe: LOADING")
                    newslistSwipeContainer.isRefreshing = true
                }
            }
        })
    }

    override fun onClickedItem(item: NewsListEntity) {
        comingSoon("\nNews Detail Page")
    }

    override fun onRefresh() {
        onReSearch(query)
    }

    fun onReSearch(_query:String) {
        query = _query
        adapter.getItems().clear()
        adapter.notifyDataSetChanged()

        val hashMap:HashMap<String,String> = HashMap<String,String>()
        hashMap.put("query", _query)
        hashMap.put("page", "1")

        viewModel.start(hashMap)
        onGetSourcesObserver()
    }

}