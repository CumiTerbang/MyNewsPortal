package com.haryop.mynewsportal.ui

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.view.LayoutInflater
import android.widget.ImageView
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.data.entities.CategoryEntity
import com.haryop.mynewsportal.databinding.ActivityTitleScreenBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TitleScreenActivity : BaseActivityBinding<ActivityTitleScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTitleScreenBinding
        get() = ActivityTitleScreenBinding::inflate

    override fun setupView(binding: ActivityTitleScreenBinding) {
        setUpBackground()
        setUpAction()
    }

    fun setUpBackground() = with(binding) {

        var category_images = resources.obtainTypedArray(R.array.news_categories_images_array)

        titleScreenCarouselView.pageCount = category_images.indexCount
        titleScreenCarouselView.setImageClickListener(object : ImageListener, ImageClickListener {
            override fun setImageForPosition(position: Int, imageView: ImageView?) {
                imageView?.setImageResource(category_images.getResourceId(position, 0))
            }

            override fun onClick(position: Int) {
                //do nothing
            }
        })

    }

    fun setUpAction() = with(binding) {
        getStart.setOnClickListener { openCategory() }
        about.setOnClickListener { openAbout() }
    }

    fun openCategory() {
        var intent = Intent(this@TitleScreenActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun openAbout() {
        var intent = Intent(this@TitleScreenActivity, AboutActivity::class.java)
        startActivity(intent)
    }

}