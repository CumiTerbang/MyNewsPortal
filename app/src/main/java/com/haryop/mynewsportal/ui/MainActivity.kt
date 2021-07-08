package com.haryop.mynewsportal.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.databinding.ActivityMainBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import dagger.hilt.android.AndroidEntryPoint

interface ToolbarTitleListener {
    fun onUpdateTitle(source_name: String, category_name: String, isMarquee: Boolean)
}

@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>(), ToolbarTitleListener,
    Toolbar.OnMenuItemClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView(binding: ActivityMainBinding) {
        setUpFragments(binding.root)
    }

    fun setUpFragments(view: View) = with(binding) {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_category_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navController.setGraph(R.navigation.main_nav_graph)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
        }

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(navController.graph)
        mainToolbar.setupWithNavController(navController, appBarConfiguration)
        mainToolbar.inflateMenu(R.menu.menu_main_activity)
        mainToolbar.setOnMenuItemClickListener(this@MainActivity)
    }

    override fun onUpdateTitle(source_name: String, category_name: String, isMarquee: Boolean) = with(binding) {
        mainToolbar.title = source_name
        mainToolbar.subtitle = category_name

        if (isMarquee) {
            findViewById<Toolbar>(R.id.main_toolbar)?.let {
                setToolbarTextViewsMarquee(it)
            }
        }
    }

    fun setToolbarTextViewsMarquee(toolbar: Toolbar) {
        for (child in toolbar.children) {
            if (child is TextView) {
                setMarquee(child)
            }
        }
    }

    fun setMarquee(textView: TextView) {
        textView.ellipsize = TextUtils.TruncateAt.MARQUEE
        textView.isSelected = true
        textView.marqueeRepeatLimit = -1
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.action_search -> {
                comingSoon("\nSearch News Page")
            }
        }
        return false
    }

}