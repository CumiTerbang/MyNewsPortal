package com.haryop.mynewsportal.ui.searchpage

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.FragmentNewslistPageBinding
import com.haryop.mynewsportal.ui.newsdetail.NewsDeatailActivity
import com.haryop.mynewsportal.utils.ConstantsObj
import com.haryop.mynewsportal.utils.EndlessRecyclerViewScrollListener
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.NewsListAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.roundToInt


@AndroidEntryPoint
class SearchFragment : BaseFragmentBinding<FragmentNewslistPageBinding>(),
    NewsListAdapter.NewsListItemListener, SwipeRefreshLayout.OnRefreshListener {

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
            val hashMap: HashMap<String, String> = HashMap<String, String>()
            hashMap.put("query", it)
            hashMap.put("page", "1")
            is_added = false
            viewModel.start(hashMap)
            Log.e("onGetSourcesObserver", "onGetSourcesObserver(1)")
            onGetSourcesObserver(1)
        }
    }

    private var isLoading = true
    var total_articles: Int = 0
    var page_param = 1
    var totalpage = 0
    private lateinit var adapter: NewsListAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    fun setUpRecyclerView() = with(viewbinding) {
        adapter = NewsListAdapter(this@SearchFragment)
        adapter.setCurrent_page(adapter.SEARCH_PAGE)

        var linearLayoutManager = LinearLayoutManager(requireContext())
        newslistRecyclerView.layoutManager = linearLayoutManager
        newslistRecyclerView.adapter = adapter

        newslistSwipeContainer.setOnRefreshListener(this@SearchFragment)

        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                comingSoon("\nDO LOAD MORE" +
//                        "\npage=${page}")
                if (isLoading == false) {
                    doLoadMore()
                }

            }
        }

//        scrollListener = object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                if (dy > 0) {
//                    val visibleItemCount: Int = linearLayoutManager.getChildCount()
//                    val totalItemCount: Int = linearLayoutManager.getItemCount()
//                    val firstVisiblesItems: Int =
//                        linearLayoutManager.findFirstVisibleItemPosition()
//
//                    if (isLoading==false && (visibleItemCount + firstVisiblesItems) >= totalItemCount) {
//                        doLoadMore()
//
//                    }
//                }
//            }
//        }

        newslistRecyclerView.addOnScrollListener(scrollListener)

    }

    fun doLoadMore() {
        isLoading = true;

        var page_toload = page_param + 1

        Log.e("load more", "page: ${page_param}")
        Log.e("load more", "page toload: ${page_toload}")
        Log.e("load more", "total page: ${totalpage}")

        if (page_toload <= totalpage) {
            page_param = page_toload
            getData(query, page_toload)
        }

    }

    private val viewModel: SearchViewModel by viewModels()
    private var items = ArrayList<Any>()
    private var is_added = false
    private fun onGetSourcesObserver(_page: Int) = with(viewbinding) {
        viewModel.getEverything.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    isLoading = false
                    newslistSwipeContainer.isRefreshing = false
                    Log.e("count article", "it.data: ${it.data}")
                    Log.e("count article", "is_added: ${is_added}")
                    if (it.data != null && is_added == false) {
                        is_added = true
                        Log.e("count article", "status: ${Resource.Status.SUCCESS}")
                        total_articles = it.data.totalResults
                        totalpage = (total_articles / ConstantsObj.EVERYTHING_PAGE_SIZE).toFloat()
                            .roundToInt()
                        if (!it.data.articles.isNullOrEmpty()) {
                            var oldcount = items.size
                            items.addAll(ArrayList(it.data.articles))
                            if (_page == totalpage) {
                                items.add(adapter.BOTTOMSPACE_LAYOUT)
                            }
                            Log.e("count article", "page: ${_page}")
                            Log.e("count article", "total page: ${totalpage}")
                            Log.e("count article", "oldcount: ${oldcount}")
                            Log.e("count article", "currentsize: ${items.size}")
                            if (_page == 1) {
                                adapter.setItems(items)
                            } else {
                                adapter.addItems(ArrayList(it.data.articles))
                            }
                            Log.e("count article", "adapter count: ${adapter.itemCount}")
                        } else {
                            Timber.e("getSearchEndpoint.observe: SUCCESS tapi null")
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    isLoading = false
                    newslistSwipeContainer.isRefreshing = false
                    Timber.e("getSearchEndpoint.observe: error")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    isLoading = false
                    Timber.e("getSearchEndpoint.observe: LOADING")
                    newslistSwipeContainer.isRefreshing = true
                }
            }
        })
    }

    override fun onClickedItem(item: NewsListEntity) {
        var intent = Intent(activity, NewsDeatailActivity::class.java)
        intent.putExtra("item", item);
        startActivity(intent)
    }

    override fun onRefresh() {
        onReSearch(query)
    }

    fun onReSearch(_query: String) {
        query = _query
        adapter.getItems().clear()
        adapter.notifyDataSetChanged()

        items.clear()

        page_param = 1
        getData(_query, 1)
    }

    fun getData(_query: String, _page: Int) {
        adapter.getItems().clear()
        adapter.notifyDataSetChanged()

        val hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("query", _query)
        hashMap.put("page", _page.toString())

        Log.e("onGetSourcesObserver", "hashMap size= (${hashMap.size})")
        Log.e("onGetSourcesObserver", "onGetSourcesObserver(${_page})")

        is_added = false
        viewModel.start(hashMap)
        onGetSourcesObserver(_page)
    }

}