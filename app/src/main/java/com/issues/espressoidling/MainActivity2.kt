package com.issues.espressoidling

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource
import com.sophos.smsec.core.resources.ui.CustomDialog
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity2 : AppCompatActivity(), CustomDialog.CustomDialogFragmentCallback {

    var espressoTestIdlingResource = CountingIdlingResource("test")
    lateinit var dialog : AlertDialog
    lateinit var dialog2 : AlertDialog

    /** Stores an instance of the dialog to be shown.  */
    lateinit var mCustomDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)
        showCustomDialog()
        showDelay()
    }

    public fun showDelay() {
        espressoTestIdlingResource.increment();
        Timer("SettingUp0", false).schedule(2000) {
            runOnUiThread {
                BuildMeAnAlertDialog2()
            }
        }
        Timer("SettingUp", false).schedule(15000) {
            runOnUiThread {
                dialog2.dismiss()
                espressoTestIdlingResource.decrement();
            }
        }
    }

    private fun showCustomDialog() {
        BuildMeAnAlertDialog()
        var fragmentId = "MainActivity2" + this.hashCode().toString()
        val ft = supportFragmentManager.beginTransaction()
        mCustomDialog = CustomDialog.newInstance(fragmentId)
        ft.add(mCustomDialog, fragmentId)
        if (!isFinishing) {
            ft.commit()
        }
    }

    private fun BuildMeAnAlertDialog() {
        val builder = AlertDialog.Builder(this@MainActivity2)
        builder.setTitle("Issue title 1")
        builder.setMessage("Are you sure you want to wait?")
        builder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { dialog, whichButton ->
                // Do nothing here. We override the callback. Workaround because alert dialogs close the dialog automatically with each button selection.
            })
        dialog = builder.create()
    }

    private fun BuildMeAnAlertDialog2() {
        val builder = AlertDialog.Builder(this@MainActivity2)
        builder.setTitle("Delay")
        builder.setMessage("Delay...")
        dialog2 = builder.create()
        dialog2.show()
    }

    fun getIdlingResource(): CountingIdlingResource {
        return espressoTestIdlingResource
    }

    override fun onDismiss() {
//        finish()
    }

    override val alertDialog: AlertDialog?
        get() = dialog
}