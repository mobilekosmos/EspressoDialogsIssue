package com.issues.espressoidling

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sophos.smsec.core.resources.ui.CustomDialog

class MainActivity : AppCompatActivity(), CustomDialog.CustomDialogFragmentCallback {

    lateinit var mCustomDialog: CustomDialog
    lateinit var mAlertDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showCustomDialog()
//        setContentView(R.layout.activity_main)
    }

    private fun showCustomDialog() {
        buildMeAnAlertDialog()
        var fragmentId = "MainActivity" + this.hashCode().toString()
        val ft = supportFragmentManager.beginTransaction()
        mCustomDialog = CustomDialog.newInstance(fragmentId)
        ft.add(mCustomDialog, fragmentId)
        if (!isFinishing) {
            ft.commit()
        }
    }

    private fun buildMeAnAlertDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        val v: View = layoutInflater.inflate(R.layout.dialog_enter_text, null)
        builder.setView(v)
        builder.setTitle("Issue title 1")
        builder.setMessage("Are you sure you want to wait?")
        builder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { dialog, whichButton ->
                // Do nothing here. We override the callback. Workaround because alert dialogs close the dialog automatically with each button selection.
            })
        mAlertDialog = builder.create()
    }

    override fun onDismiss() {
        // ignore
    }

    override val alertDialog: AlertDialog?
        get() = mAlertDialog
}