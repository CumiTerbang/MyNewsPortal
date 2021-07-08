package com.haryop.mynewsportal.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.haryop.mynewsportal.databinding.ActivitySplashScreenBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity: BaseActivityBinding<ActivitySplashScreenBinding>() {

    val activityScope = CoroutineScope(Dispatchers.Main)

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate

    override fun setupView(binding: ActivitySplashScreenBinding) {
        setupAction(binding.root)
    }

    fun setupAction(view: View) = with(binding) {
        activityScope.launch {
            delay(2000)
            var intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}