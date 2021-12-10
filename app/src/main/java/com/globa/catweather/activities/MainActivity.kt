package com.globa.catweather.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.globa.catweather.R
import com.globa.catweather.view.OnSwipeTouchListener


class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityLayout = findViewById(R.id.mainActivityLayout)

        mainActivityLayout.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                val intent = Intent(applicationContext, ForecastWeatherActivity::class.java)
                startActivity(intent)
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                val intent = Intent(applicationContext, DetailWeatherActivity::class.java)
                startActivity(intent)

            }
            override fun onSwipeUp() {
                super.onSwipeUp()
                Toast.makeText(this@MainActivity, "Swipe up gesture detected", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
                Toast.makeText(this@MainActivity, "Swipe down gesture detected", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}