package com.haryop.synpulsefrontendchallenge.utils

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentActivityBinding<T : ViewBinding> : FragmentActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> T

    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = _binding as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setupView(binding)
    }

    abstract fun setupView(binding: T)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}