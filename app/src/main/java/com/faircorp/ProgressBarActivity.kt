package com.faircorp

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

/*
    Child activity must have progress bar in the layout
    TODO: dynamically add progress bar
 */
open class ProgressBarActivity : BasicActivity(){
    protected fun hideAllAndShowProgressBar() {
        // constraint layout
        var rootView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        for (index in 0 until (rootView as ViewGroup).childCount) {
            val nextChild = (rootView as ViewGroup).getChildAt(index)
            if (nextChild !is ProgressBar) {
                nextChild.visibility = View.INVISIBLE
            } else {
                nextChild.visibility = View.VISIBLE
            }
        }
    }

    protected fun showAllAndHideProgressBar() {
        // constraint layout
        var rootView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        for (index in 0 until (rootView as ViewGroup).childCount) {
            val nextChild = (rootView as ViewGroup).getChildAt(index)
            if (nextChild !is ProgressBar) {
                nextChild.visibility = View.VISIBLE
            } else {
                nextChild.visibility = View.INVISIBLE
            }
        }
    }
}