package com.annikadietz.shoppy_shoppingbuddy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.annikadietz.shoppy_shoppingbuddy.ui.login.LoginFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.register.RegisterFragment
import java.util.*

class AuthActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val viewPager: ViewPager = findViewById(R.id.viewPager)

        val pagerAdapter =
            AuthenticationPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragmet(LoginFragment())
        pagerAdapter.addFragmet(RegisterFragment())
        viewPager.adapter = pagerAdapter
    }

    internal class AuthenticationPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!) {
        private val fragmentList: ArrayList<Fragment> = ArrayList<Fragment>()
        override fun getItem(i: Int): Fragment {
            return fragmentList[i]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

//        val count: Int

        fun addFragmet(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }
}