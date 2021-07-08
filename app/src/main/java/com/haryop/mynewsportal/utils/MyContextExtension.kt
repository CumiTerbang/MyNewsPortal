package com.haryop.synpulsefrontendchallenge.utils

import android.R
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.comingSoon(activity: Activity, msg: String=""){
    val toast = Toast.makeText(activity, "COMING SOON${msg}", Toast.LENGTH_SHORT)
    val v = toast.view!!.findViewById<View>(R.id.message) as TextView
    if (v != null) v.gravity = Gravity.CENTER
    toast.show()

}

fun Fragment.comingSoon(msg: String) {
    activity?.comingSoon(requireActivity(), msg)
}

fun Activity.comingSoon(msg: String) {
    comingSoon(this, msg)
}

