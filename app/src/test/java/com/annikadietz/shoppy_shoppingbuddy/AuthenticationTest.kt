package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.test.platform.app.InstrumentationRegistry
import com.annikadietz.shoppy_shoppingbuddy.ui.login.LoginFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.register.RegisterFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.register_fragment.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class AuthenticationTest {
    private lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun registerUser_Test() {
        val email = EditText(instrumentationContext)
        email.setText("stephantest@ruiz.com")
        val password = EditText(instrumentationContext)
        password.setText("stephan")
        val registerFrag = RegisterFragment()


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