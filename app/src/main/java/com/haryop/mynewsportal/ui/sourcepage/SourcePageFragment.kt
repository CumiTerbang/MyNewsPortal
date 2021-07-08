package com.haryop.mynewsportal.ui.sourcepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.haryop.mynewsportal.databinding.FragmentSourcePageBinding
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.SourceAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SourcePageFragment : BaseFragmentBinding<FragmentSourcePageBinding>(),
    SourceAdapter.SourceItemListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSourcePageBinding
        get() = FragmentSourcePageBinding::inflate

    lateinit var viewBinding: FragmentSourcePageBinding
    var category: String = ""
    override fun setupView(binding: FragmentSourcePageBinding) {
        viewBinding = binding

        arguments?.getString("category")?.let {
            viewModel.start(it)
        }
        setUpRecyclerView()
        onGetSourcesObserver()
    }

    private lateinit var adapter: SourceAdapter
    fun setUpRecyclerView() = with(viewBinding) {
        adapter = SourceAdapter(this@SourcePageFragment)
        sourceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        sourceRecyclerView.adapter = adapter
    }

    private val viewModel: SourceViewModel by viewModels()
    private fun onGetSourcesObserver()=with(viewBinding) {
        viewModel.getSearchEndpoint.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
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
                    progressBar.visibility = View.GONE
                    Timber.e("getSearchEndpoint.observe: error")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Timber.e("getSearchEndpoint.observe: LOADING")
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onClickedSource(source: String) {
        TODO("Not yet implemented")
    }

}