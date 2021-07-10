package com.haryop.mynewsportal.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.databinding.ActivityTitleScreenBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityFullWindowBinding
import com.synnapps.carouselview.ImageListener


class TitleScreenActivity : BaseActivityFullWindowBinding<ActivityTitleScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTitleScreenBinding
        get() = ActivityTitleScreenBinding::inflate

    override fun setupView(binding: ActivityTitleScreenBinding) {
        setUpBackground()
        setUpAction()
    }

    var category_images = intArrayOf(
        R.drawable.ctgry_technology,
        R.drawable.ctgry_business,
        R.drawable.ctgry_entertainment,
        R.drawable.ctgry_general,
        R.drawable.ctgry_health,
        R.drawable.ctgry_science,
        R.drawable.ctgry_sports,
        R.drawable.ctgry_technology,
        R.drawable.ctgry_business
    )

    fun setUpBackground() = with(binding) {
        titleScreenCarouselView.pageCount = category_images.size
        titleScreenCarouselView.setImageListener(object : ImageListener {
            override fun setImageForPosition(position: Int, imageView: ImageView) {
                imageView.setImageResource(category_images[position])
            }
        })

        titleScreenCarouselView.setPageTransformer(object : ViewPager.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                // Ensures the views overlap each other.
                page.setTranslationX(page.getWidth() * -position);
                page.scaleX = 1f
                page.scaleY = 1f
                val scaleAnimation = ScaleAnimation(
                    1f, 1.1f, 1f, 1.1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f
                )

                // Alpha property is based on the view position.
                if (position <= -1.0F || position >= 1.0F) {
                    page.setAlpha(0.0F);
                } else if (position == 0.0F) {
                    page.setAlpha(1.0F);
                } else { // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    page.setAlpha(1.0F - Math.abs(position));
                }

                scaleAnimation.duration = 8000
                page.animation = scaleAnimation
                page.scaleX = 1.1f
                page.scaleY = 1.1f

//                page.setAlpha(0f);
//                page.setVisibility(View.VISIBLE);
//
//                // Start Animation for a short period of time
//                page.animate()
//                    .alpha(1f)
//                    .setDuration(page.getResources().getInteger(android.R.integer.config_shortAnimTime).toLong())

            }
        })

        titleScreenCarouselView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //do nothing
            }

            override fun onPageSelected(position: Int) {
                // skip fake page (first), go to last page
                if (position === 0) {
                    titleScreenCarouselView.setCurrentItem(category_images.size - 2)
                }

                // skip fake page (last), go to first page
                if (position === category_images.size - 1) {
                    titleScreenCarouselView.setCurrentItem(1) //notice how this jumps to position 1, and not position 0. Position 0 is the fake page!
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                //do nothing
            }


        })

        titleScreenCarouselView.setOnTouchListener(null)
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