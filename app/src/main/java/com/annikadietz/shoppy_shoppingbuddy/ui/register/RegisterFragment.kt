package com.annikadietz.shoppy_shoppingbuddy.ui.register

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment(var viewPager: ViewPager2) : Fragment() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)
        val addProductButton: Button = root.findViewById(R.id.btn_register)
        addProductButton.setOnClickListener {
            register()
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun register() {
        if (emptyCheck("Enter username", username.text.toString())) {
            return
        }

        if (emptyCheck("Enter email", email.text.toString())) {
            return
        }

        if (emptyCheck("Enter password", password.text.toString())) {
            return
        }

        if (emptyCheck("Re-enter password", repassword.text.toString())) {
            return
        }

        if (passwordLengthCheck(
                "Password too short, enter minimum 6 characters",
                password.text.toString().length,
                6
            )
        ) {
            return
        }

        if (!passwordMatchCheck(
                "Passwords must match, please enter password again",
                password.text.toString(),
                repassword.text.toString()
            )
        ) {
            return
        }
        progressBar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener {
                var task = it
                if (task.isSuccessful) {
                    mAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "User registered successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewPager.currentItem = 0
                        } else {
                            Toast.makeText(
                                activity,
                                "User not registered - problem with you email.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        progressBar.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(activity, "User not registered.", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.GONE
            }
    }

    private fun passwordMatchCheck(message: String, password: String, repassword: String): Boolean {
        return if (password != repassword) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun registerUser(email: EditText, password: EditText): Task<AuthResult>? {
        return mAuth.createUserWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()
        )
    }


    private fun emptyCheck(message: String, str: String): Boolean {
        return if (TextUtils.isEmpty(str)) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }

    private fun passwordLengthCheck(message: String, length: Int, minLength: Int): Boolean {
        return if (length < minLength) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }

}