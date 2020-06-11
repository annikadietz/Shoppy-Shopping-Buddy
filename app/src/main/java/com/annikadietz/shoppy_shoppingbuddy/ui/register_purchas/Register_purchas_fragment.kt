package com.annikadietz.shoppy_shoppingbuddy.ui.register_purchas

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.ProductSearchViewModel
import java.lang.System.exit

class Register_purchas_fragment : Fragment() {
    var messageText = ""
    private lateinit var alertDialog: AlertDialog
    companion object {
        fun newInstance() =
            Register_purchas_fragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        val root =inflater.inflate(R.layout.register_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.product_info)
        viewModel.text.observe(this, Observer {
            textView.text = it
        })
        val registerResultLayout: LinearLayout = root.findViewById(R.id.product_list)
        val registerTermTextField: TextView = root.findViewById(R.id.product_info)
        val registerProductButton: Button = root.findViewById(R.id.register_btn)

        registerProductButton.setOnClickListener {
           messageText = registerTermTextField.text.toString()
            showCustomDialog()
          //  DatabaseHelper.search(registerTermTextField.text.toString(), registerResultLayout)
        }
        return root
    }
    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(messageText)
        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val b = dialogBuilder.create()
        b.show()
    }


    fun showCustomDialog() {
        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.dialog_custom_view, null)
        val product_name = dialogView.findViewById<TextView>(R.id.product_name)
        product_name.text = messageText
        val price = dialogView.findViewById<TextView>(R.id.price)
        price.text="price"
        val enter_new_price: TextView = dialogView.findViewById(R.id.enter_new_price)
        enter_new_price.text.toString()
        val price_suggestion = dialogView.findViewById<TextView>(R.id.price_suggestion)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroup)
        var text = "You selected: "
        radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = dialogView.findViewById(checkedId)
           text += radio.text
//            Toast.makeText(activity," text: ${radio.text}",
//                Toast.LENGTH_SHORT).show()
        }


        val custom_button: Button = dialogView.findViewById(R.id.register_purchase_btn)
        custom_button.setOnClickListener {
            //perform custom action
            price_suggestion.text=text
        }
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!)

        dialogBuilder.setPositiveButton("Done",
            DialogInterface.OnClickListener { dialog, whichButton -> })

        dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(arg0: DialogInterface) {


            }
        })
        dialogBuilder.setView(dialogView)
        alertDialog = dialogBuilder.create();
       // alertDialog.window!!.getAttributes().windowAnimations = R.style.PauseDialogAnimation
        alertDialog.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}