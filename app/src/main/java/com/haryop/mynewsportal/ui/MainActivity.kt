package com.haryop.mynewsportal.ui

import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.databinding.ActivityMainBinding
import com.haryop.mynewsportal.ui.searchpage.SearchActivity
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            blackscreen.visibility = View.GONE
            mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()

            if (destination.id == R.id.categoryPageFragment) {
                mainToolbar.subtitle = ""
                isLastFragment = true
            } else {
                isLastFragment = false
            }
        }

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(navController.graph)
        mainToolbar.setupWithNavController(navController, appBarConfiguration)
        mainToolbar.inflateMenu(R.menu.menu_main_activity)
        mainToolbar.setOnMenuItemClickListener(this@MainActivity)
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

    override fun onUpdateTitle(source_name: String, category_name: String, isMarquee: Boolean) =
        with(binding) {
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
                var searchView: SearchView = item.actionView as SearchView
                searchView.queryHint = "search articles..."
                searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        openSearchPage("\n" + query)

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

    fun openSearchPage(query: String) {
        var intent = Intent(this@MainActivity, SearchActivity::class.java)
        intent.putExtra("query", query);
        startActivity(intent)
    }

    var isLastFragment: Boolean = false
    private var doubleBackToExitPressedOnce = false
    val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onBackPressed() {
        if (binding.blackscreen.visibility == View.VISIBLE) {
            binding.blackscreen.visibility = View.GONE
            binding.mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()
        } else if (isLastFragment) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            activityScope.launch {
                delay(2000)
                doubleBackToExitPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }

    }

}