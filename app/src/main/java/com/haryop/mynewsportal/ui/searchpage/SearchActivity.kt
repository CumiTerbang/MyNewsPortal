package com.haryop.mynewsportal.ui.searchpage

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.databinding.ActivityMainBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : BaseActivityBinding<ActivityMainBinding>(), Toolbar.OnMenuItemClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    var query = ""
    override fun setupView(binding: ActivityMainBinding) {
        query = intent.getStringExtra("query").toString()
        setUpFragments(binding.root)
    }

    fun setUpFragments(view: View) = with(binding) {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_category_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navController.setGraph(R.navigation.search_nav_graph, bundleOf("query" to query))
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
        }

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(navController.graph)
        mainToolbar.setupWithNavController(navController, appBarConfiguration)

        mainToolbar.inflateMenu(R.menu.menu_main_activity)
        mainToolbar.setOnMenuItemClickListener(this@SearchActivity)
        mainToolbar.setNavigationOnClickListener {
            blackscreen.visibility = View.GONE
            mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()
            navController.popBackStack()
        }

        mainToolbar.menu.findItem(R.id.action_search)
            ?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    blackscreen.visibility = View.VISIBLE
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    blackscreen.visibility = View.GONE
                    return true
                }

            })

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.action_search -> {
                var searchView: SearchView = item.actionView as SearchView
                searchView.queryHint = "search articles..."
                searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        reSearchPage("\n" + query)

                        if (!searchView.isIconified()) {
                            searchView.setIconified(true);
                        }
                        item.collapseActionView();
                        return false;
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
            }
        }
        return false
    }

    fun reSearchPage(_query: String) = with(binding) {
        query = _query
        var searchFragment:SearchFragment = getForegroundFragment() as SearchFragment
        searchFragment.onReSearch(_query)
    }

    fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_category_fragment)
        return if (navHostFragment == null) null else navHostFragment.getChildFragmentManager()
            .getFragments().get(0)
    }

    override fun onBackPressed() {
        if (binding.blackscreen.visibility == View.VISIBLE) {
            binding.blackscreen.visibility = View.GONE
            binding.mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()
        }else{
            super.onBackPressed()
        }
    }

}