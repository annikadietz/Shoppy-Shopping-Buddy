package com.annikadietz.shoppy_shoppingbuddy.ui.register

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.annikadietz.shoppy_shoppingbuddy.MainActivity
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null

    companion object {
        fun newInstance() =
            RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.register_fragment, container, false)
        val addProductButton: Button = root.findViewById(R.id.btn_register)
        addProductButton.setOnClickListener {
            register()
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)


        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.getCurrentUser() != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }
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

        if (passwordLengthCheck("Password too short, enter minimum 6 characters", password.text.toString().length, 6)) {
            return
        }

        if (!passwordMatchCheck("Passwords must match, please enter password again", password.text.toString(), repassword.text.toString())) {
            return
        }

        progressBar.visibility = View.VISIBLE
        registerUser(email, password)!!
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    Toast.makeText(activity, "Authentication success." + task.isSuccessful,
                        Toast.LENGTH_LONG).show()
                        startActivity(Intent(activity, MainActivity::class.java))
                        activity?.finish()
                } else {
                    Toast.makeText(activity, "Authentication failed." + task.exception,
                        Toast.LENGTH_LONG).show()
                }
            })
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
        return mAuth?.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
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