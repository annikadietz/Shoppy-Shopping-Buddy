package com.annikadietz.shoppy_shoppingbuddy.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.annikadietz.shoppy_shoppingbuddy.MainActivity
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val addProductButton: Button = root.findViewById(R.id.btn_login)
        addProductButton.setOnClickListener {
            loginButtonClicked()
        }

        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }

        return root
    }

    private fun loginButtonClicked() {
        if (emptyCheck("Enter email address!", email.text.toString())) {
            return
        }

        if (emptyCheck("Enter password!", password.text.toString())) {
            return
        }

        progressBar.visibility = View.VISIBLE
        loginUser(email, password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if(task.result?.user!!.isEmailVerified){
                    Toast.makeText(activity, "Logged in!", Toast.LENGTH_SHORT).show()
                    NewDatabaseHelper.uid = task.result?.user!!.uid
                    val intent = Intent(this.requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(activity, "Verify your email and try again.", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE

            } else {
                if (password.text.toString().length < 6) {
                    password.error = getString(R.string.minimum_password)
                } else {
                    Log.w("auth", task.exception.toString())
                    Toast.makeText(activity, "FAILED", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    fun loginUser(email: EditText, password: EditText): Task<AuthResult>? {
        return mAuth?.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
    }

    private fun emptyCheck(message: String, str: String): Boolean {
        return if (TextUtils.isEmpty(str)) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }


}