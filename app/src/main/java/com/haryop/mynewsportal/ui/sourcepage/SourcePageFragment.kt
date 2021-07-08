package com.haryop.mynewsportal.ui.sourcepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.databinding.FragmentSourcePageBinding
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.SourceAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SourcePageFragment : BaseFragmentBinding<FragmentSourcePageBinding>(),
    SourceAdapter.SourceItemListener, SwipeRefreshLayout.OnRefreshListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSourcePageBinding
        get() = FragmentSourcePageBinding::inflate

    lateinit var viewBinding: FragmentSourcePageBinding
    var category: String = ""
    override fun setupView(binding: FragmentSourcePageBinding) {
        viewBinding = binding

        setUpRecyclerView()
        arguments?.getString("category")?.let {
            category = it
            viewModel.start(it)
            onGetSourcesObserver()
        }
    }

    private lateinit var adapter: SourceAdapter
    fun setUpRecyclerView() = with(viewBinding) {
        adapter = SourceAdapter(this@SourcePageFragment)
        sourceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        sourceRecyclerView.adapter = adapter

        sourceSwipeContainer.setOnRefreshListener(this@SourcePageFragment)
    }

    private val viewModel: SourceViewModel by viewModels()
    private fun onGetSourcesObserver() = with(viewBinding) {
        viewModel.getSource.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    sourceSwipeContainer.isRefreshing = false
                    if (!it.data.isNullOrEmpty()) {
                        var items = ArrayList<Any>()
                        items.addAll(ArrayList(it.data))
                        items.add(adapter.ITEM_TYPE_BOTTOMSPACE_LAYOUT)
                        adapter.setItems(items)
                    } else {
                        Timber.e("getSearchEndpoint.observe: SUCCESS tapi null")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                Resource.Status.ERROR -> {
                    sourceSwipeContainer.isRefreshing = false
                    Timber.e("getSearchEndpoint.observe: error")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Timber.e("getSearchEndpoint.observe: LOADING")
                    sourceSwipeContainer.isRefreshing = true
                }
            }
        })
    }

    override fun onClickedSource(source_id: String, source_name: String) {
        findNavController().navigate(
            R.id.action_source_to_newslist,
            bundleOf(
                "source_id" to source_id,
                "source_name" to source_name,
                "category" to category
            )
        )
    }

    override fun onExpandItem(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun onRefresh() {
        adapter.getItems().clear()
        adapter.notifyDataSetChanged()
        viewModel.start(category)
        onGetSourcesObserver()
        adapter.notifyDataSetChanged()
    }

}