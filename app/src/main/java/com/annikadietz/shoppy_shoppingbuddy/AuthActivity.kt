package com.annikadietz.shoppy_shoppingbuddy

import android.os.Bundle
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.annikadietz.shoppy_shoppingbuddy.ui.login.LoginFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.register.RegisterFragment
import java.util.*

class AuthActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        val pagerAdapter =
            AuthenticationPagerAdapter(
                this
            )
        pagerAdapter.addFragmet(LoginFragment())
        pagerAdapter.addFragmet(RegisterFragment())
        viewPager.adapter = pagerAdapter
    }

    internal class AuthenticationPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
        private val fragmentList: ArrayList<Fragment> = ArrayList<Fragment>()

        fun addFragmet(fragment: Fragment) {
            fragmentList.add(fragment)
        }

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }
}