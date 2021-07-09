package com.haryop.mynewsportal.ui.newslistpage

import android.content.Intent
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
import com.haryop.mynewsportal.ui.ToolbarTitleListener
import com.haryop.mynewsportal.ui.newsdetail.NewsDeatailActivity
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.NewsListAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewsListPageFragment : BaseFragmentBinding<FragmentNewslistPageBinding>(),
    NewsListAdapter.NewsListItemListener, SwipeRefreshLayout.OnRefreshListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewslistPageBinding
        get() = FragmentNewslistPageBinding::inflate

    lateinit var viewbinding: FragmentNewslistPageBinding
    var category_name = ""
    var source_name = ""
    var source_id = ""
    override fun setupView(binding: FragmentNewslistPageBinding) {
        viewbinding = binding
        setUpRecyclerView()
        setupToolbar()
    }

    fun setupToolbar() {
        arguments?.getString("category")?.let {
            category_name = it
        }
        arguments?.getString("source_name")?.let {
            source_name = it
        }
        arguments?.getString("source_id")?.let {
            source_id = it
            viewModel.start(it)
            onGetSourcesObserver()
        }
        (activity as ToolbarTitleListener).onUpdateTitle(source_name, category_name, true)
    }

    private var loading = true
    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var page_param = 1
    private lateinit var adapter: NewsListAdapter
    fun setUpRecyclerView() = with(viewbinding) {
        adapter = NewsListAdapter(this@NewsListPageFragment)
        adapter.setCurrent_page(adapter.NEWSLIST_PAGE)
        var layManager = LinearLayoutManager(requireContext())
        newslistRecyclerView.layoutManager = layManager
        newslistRecyclerView.adapter = adapter

        newslistSwipeContainer.setOnRefreshListener(this@NewsListPageFragment)

        newslistRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layManager.getChildCount();
                    totalItemCount = layManager.getItemCount();
                    pastVisiblesItems = layManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            // Do pagination.. i.e. fetch new data
                            page_param++
                            comingSoon("\nDO NEXT PAGE")

                            loading = true;
                        }
                    }
                }
            }
        })
    }

    private val viewModel: NewsListViewModel by viewModels()
    private fun onGetSourcesObserver() = with(viewbinding) {
        viewModel.getHeadlines.observe(viewLifecycleOwner, Observer {
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
        var intent = Intent(activity, NewsDeatailActivity::class.java)
        intent.putExtra("item", item);
        startActivity(intent)
    }

    override fun onRefresh() {
        adapter.getItems().clear()
        adapter.notifyDataSetChanged()
        viewModel.start(source_id)
        onGetSourcesObserver()
    }


}