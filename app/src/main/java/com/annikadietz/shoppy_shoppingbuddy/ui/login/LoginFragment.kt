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
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.email
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_login.progressBar
import kotlinx.android.synthetic.main.register_fragment.*
import java.util.concurrent.Executor


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
        loginUser(email, password)?.addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
    //                        val intent = Intent(this, MainActivity::class.java)
    //                        startActivity(intent)
    //                        finish()
    //                        task.result.
                Log.w("auth", task.result.toString())
                Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show()
            } else {
                if (password.text.toString().length < 6) {
                    password.error = getString(R.string.minimum_password)
                } else {
                    Log.w("auth", task.exception.toString())
                    Toast.makeText(activity, "FAILED", Toast.LENGTH_LONG).show()
                }
            }
        })
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