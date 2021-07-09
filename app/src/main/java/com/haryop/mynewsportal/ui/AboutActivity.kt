package com.haryop.mynewsportal.ui

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.core.app.NavUtils
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.databinding.ActivityAboutBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding


class AboutActivity : BaseActivityBinding<ActivityAboutBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityAboutBinding
        get() = ActivityAboutBinding::inflate

    override fun setupView(binding: ActivityAboutBinding) {
        setUpAction(binding.root)
        setupActionbar()
    }

    fun setUpAction(view: View) = with(binding) {
        val filename = "about_content.html"
        webviewAbout.getSettings().setJavaScriptEnabled(true);
        webviewAbout.loadUrl("file:///android_asset/" + filename);
    }

    fun setupActionbar() {
        var actionbar = supportActionBar
        actionbar?.title = resources.getString(R.string.about)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }


}