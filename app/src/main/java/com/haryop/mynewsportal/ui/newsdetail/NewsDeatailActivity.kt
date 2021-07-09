package com.haryop.mynewsportal.ui.newsdetail

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.ActivityNewsDetailBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDeatailActivity : BaseActivityBinding<ActivityNewsDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityNewsDetailBinding
        get() = ActivityNewsDetailBinding::inflate

    lateinit var item: NewsListEntity
    override fun setupView(binding: ActivityNewsDetailBinding) {
        item = intent.getSerializableExtra("item") as NewsListEntity
        setupActionbar()
        setUpFragments()
    }

    fun setUpFragments() = with(binding) {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_category_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navController.setGraph(R.navigation.detail_nav_graph, bundleOf("item" to item))
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
        }
    }

    fun setupActionbar() {
        var actionbar = supportActionBar
        actionbar?.title = item.source.name
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