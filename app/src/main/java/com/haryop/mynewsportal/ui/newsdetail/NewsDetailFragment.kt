package com.haryop.mynewsportal.ui.newsdetail

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.FragmentNewsDetailBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import com.haryop.synpulsefrontendchallenge.utils.setDate
import com.haryop.synpulsefrontendchallenge.utils.setImageGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragmentBinding<FragmentNewsDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsDetailBinding
        get() = FragmentNewsDetailBinding::inflate

    lateinit var viewbinding: FragmentNewsDetailBinding
    lateinit var item: NewsListEntity
    override fun setupView(binding: FragmentNewsDetailBinding) {
        viewbinding = binding
        setUpContent()
    }

    fun setUpContent() = with(viewbinding) {
        arguments?.getSerializable("item")?.let {
            item = it as NewsListEntity
            var desc = if(item.description.isNullOrEmpty()){""}else{item.description}
            var content = if(item.content.isNullOrEmpty()){""}else{item.content}
            setUpWebview(desc + "</br></br>" + content)
            title.text = item.title
            date.text = requireContext().setDate(item.publishedAt).toString()

            requireContext().setImageGlide(item.urlToImage, root, image)

            cont.setOnClickListener {
                openChromeCustomTab(item.url)
            }

        }
    }

    fun openChromeCustomTab(url: String) {
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(resources.getColor(R.color.peach))
            .build()

        val builder = CustomTabsIntent.Builder()
        builder.setDefaultColorSchemeParams(defaultColors)

        val customTabsIntent = builder.build()

        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    fun setUpWebview(desc: String) = with(viewbinding) {
        val filename = "about_content.html"
        detailWebview.getSettings().setJavaScriptEnabled(true);
        detailWebview.loadData(desc, "text/html", "UTF-8");
    }

}