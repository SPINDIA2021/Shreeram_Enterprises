package com.satmatgroup.shreeram.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.satmatgroup.shreeram.NewMainActivity
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs

class SplashScreenActivity : AppCompatActivity() {
    // Splash screen timer
    private val SPLASH_TIME_OUT = 3000
    var tvLogoName: TextView? = null
    var mContext: Context = this@SplashScreenActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({ // This method will be executed once the timer is over
            // Start your app main activity
            val islogin: Boolean =
                AppPrefs.getBooleanPref(AppConstants.IS_LOGIN, mContext)
            if (islogin) {
                val intent = Intent(mContext, NewMainActivity::class.java)
                startActivity(intent)
            } else {
                val i = Intent(
                    this@SplashScreenActivity, LoginActivity
                    ::class.java
                )
                startActivity(i)
            }


            // close this activity
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}