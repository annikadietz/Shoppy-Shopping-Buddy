package com.annikadietz.shoppy_shoppingbuddy.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.annikadietz.shoppy_shoppingbuddy.MainActivity
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()

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
        val resetPasswordButton: TextView = root.findViewById(R.id.reset_btn)
        resetPasswordButton.setOnClickListener {
            resetPassword()
        }


        if (mAuth!!.currentUser != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }



        return root
    }
    private fun resetPassword() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)

        val title = TextView(this.context)
        title.text = "Enter your email"
        title.setPadding(10, 70, 10, 70)
        title.gravity = Gravity.CENTER
        title.textSize = 20f
        builder.setCustomTitle(title)
        val input = EditText(this.context)

        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        builder.setView(input)

        builder.setPositiveButton("Reset password"
        ) { dialog, which ->
            var email = input.text.toString()
            mAuth?.sendPasswordResetEmail(email)?.addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        val dialog: AlertDialog = builder.create()

        input.doOnTextChanged { text, start, count, after ->
            if(Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
            } else {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            }
        }
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
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