package com.satmatgroup.shreeram.utils

import android.app.Activity
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import java.util.*

interface CommonMethods {

    companion object {
        fun getDisplaySize(activity: Activity): String? {
            var x = 0.0
            var y = 0.0
            val mWidthPixels: Int
            val mHeightPixels: Int
            try {
                val windowManager = activity.windowManager
                val display = windowManager.defaultDisplay
                val displayMetrics = DisplayMetrics()
                display.getMetrics(displayMetrics)
                val realSize = Point()
                Display::class.java.getMethod("getRealSize", Point::class.java)
                    .invoke(display, realSize)
                mWidthPixels = realSize.x
                mHeightPixels = realSize.y
                val dm = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(dm)
                x = Math.pow((mWidthPixels / dm.xdpi).toDouble(), 2.0)
                y = Math.pow((mHeightPixels / dm.ydpi).toDouble(), 2.0)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return java.lang.String.format(Locale.US, "%.2f", Math.sqrt(x + y))
        }

    }

}