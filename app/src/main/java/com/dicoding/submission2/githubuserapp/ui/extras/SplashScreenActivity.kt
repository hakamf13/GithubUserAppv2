package com.dicoding.submission2.githubuserapp.ui.extras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.submission2.githubuserapp.R
import com.dicoding.submission2.githubuserapp.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private val waktuLoading: Int = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, waktuLoading.toLong())
    }
}