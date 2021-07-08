package com.haryop.mynewsportal.ui

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.databinding.ActivityMainBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import dagger.hilt.android.AndroidEntryPoint

interface ToolbarTitleListener {
    fun onUpdateTitle(title: String)
}

@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>(), ToolbarTitleListener {

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
    }

    override fun onUpdateTitle(title: String)= with(binding) {
        mainToolbar.title = title
    }

}