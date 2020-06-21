package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewpager2.widget.ViewPager2
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper.uid
import com.annikadietz.shoppy_shoppingbuddy.ui.login.LoginFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.register.RegisterFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_auth.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class AuthenticationTest {
    private lateinit var instrumentationContext: Context


    @Before
    fun setup() {

        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        FirebaseApp.initializeApp(instrumentationContext);
    }

    @Test
    fun registerUser_Test() {
        val email = EditText(instrumentationContext)
        email.setText("stephantest@ruiz.com")
        val password = EditText(instrumentationContext)
        password.setText("stephan")
       var instrumentationContext2 = InstrumentationRegistry.getInstrumentation().context

//Todo it's not working at this moment
        val viewPager=ViewPager2(instrumentationContext2)
       val registerFrag = RegisterFragment(viewPager)
        val result = registerFrag.registerUser(email, password)
            ?.addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                val str = email.text.toString()
                val tr = task.result
                if (task.isSuccessful) {
                    println(task.result)
                    Assert.assertEquals(task.result, "")
                } else {
                    println(task.exception.toString())
                    Assert.assertEquals(task.result, "")
                }
            })?.addOnFailureListener(OnFailureListener { task ->
                task.message
            })

        println()
    }

    @Test
    fun loginUser_Test() {
        val email = EditText(instrumentationContext)
        email.setText("stephan@ruiz.com")
        val password = EditText(instrumentationContext)
        password.setText("stephan")
        val loginFrag = LoginFragment()

//        loginFrag.progressBar.progress = findViewById(R.id.progressBar)

        val result = loginFrag.loginUser(email, password)
            ?.addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    Assert.assertEquals("", "")
                } else {
                    if (password.text.toString().length < 6) {

                    } else {

                    }
                }
            })
        println()
    }

}