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
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


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

fun Context.comingSoon(activity: Activity, msg: String = "") {
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

fun Context.setDate(publishedAt: String): String {
    var date: String = publishedAt
    try {
        //publishedAt = 2021-07-08T10:30:11+00:00
        if (publishedAt.contains("Z")) {
            publishedAt.replace("Z", "")
        }

        var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val newDate: Date = format.parse(publishedAt)
        format = SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm")
        date = format.format(newDate)

    } catch (e: Exception) {
        if (date.contains("Z")) {
            date.replace("Z", "")
        }

        date.replace("T", " ")
    }

    return date
}


