package com.labawsrh.aws.introscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.*
import android.view.WindowManager.LayoutParams.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    var position: Int= 0
    lateinit var btnAnim: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }

        setContentView(R.layout.activity_intro)
        supportActionBar!!.hide()

        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)

        val mList = ArrayList<ScreenItem>()
        mList.add(ScreenItem("Fresh Food", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.img1))
        mList.add(ScreenItem("Fast Delivery", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.img2))
        mList.add(ScreenItem("Easy Payment", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.img3))

        var introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        screen_viewpager.adapter = introViewPagerAdapter
        tab_indicator.setupWithViewPager(screen_viewpager)
        btn_next.setOnClickListener {
            position = screen_viewpager!!.currentItem
            if (position < mList.size) {
                position++
                screen_viewpager!!.currentItem = position
            }

            if (position == mList.size - 1) {
                loaddLastScreen()
            }
        }

        tab_indicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loaddLastScreen()

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
        // Get Started button click listener
        btn_get_started.setOnClickListener {
            //open main activity
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            // also we need to save a boolean value to storage so next time when the user run the app
            // we could know that he is already checked the intro screen activity
            // i'm going to use shared preferences to that process
            savePrefsData()
            finish()
        }

        // skip button click listener

        tv_skip.setOnClickListener { screen_viewpager!!.currentItem = mList.size }


    }

    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        return pref.getBoolean("isIntroOpnend", false)


    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.commit()


    }

    private fun loaddLastScreen() {
        btn_next.visibility = View.INVISIBLE
        btn_get_started.visibility = View.VISIBLE
        tv_skip.visibility = View.INVISIBLE
        tab_indicator.visibility = View.INVISIBLE
        // TODO : ADD an animation the getstarted button
        // setup animation
        btn_get_started.animation = btnAnim


    }
}
