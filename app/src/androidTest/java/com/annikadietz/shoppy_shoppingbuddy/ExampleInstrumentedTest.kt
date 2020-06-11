package com.annikadietz.shoppy_shoppingbuddy

import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.view.Gravity
import androidx.core.content.MimeTypeFilter.matches
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.annikadietz.shoppy_shoppingbuddy.ui.login.LoginFragment
import androidx.fragment.app.testing.launchFragmentInContainer

@RunWith(AndroidJUnit4::class)
class ChangeTextBehaviorTest {

    private lateinit var stringToBetyped: String

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso"
    }

    @Test
    fun changeText_sameActivity() {
//        val scenario: FragmentScenario<LoginFragment> = launchFragmentInContainer<LoginFragment>()
//        scenario.recreate()

        // Type text and then press the button.

//        onView(withId(R.id.drawer_layout))
//            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
//            .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
            .perform(click())
        onView(withId(R.id.email))
            .perform(typeText("stephan@ruiz.com"), closeSoftKeyboard())
        onView(withId(R.id.password))
            .perform(typeText("stephan"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        Thread.sleep(5000)
        // Check that the text was changed.
//        onView(withId(R.id.password))
//            .check(matches(withText(stringToBetyped)))
    }
}