package com.sophos.smsec.core.resources.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Implementation of the dialog to be shown.
 *
 * @author DavidBauer
 */
class CustomDialog : DialogFragment() {
    /** Interface to communicate back to the container of this fragment.  */
    private var mCallback: CustomDialogFragmentCallback? =
        null

    /** The AlertDialog to use to build the dialog.  */
    private var mAlertDialog: AlertDialog? = null

    /** The Id to be used to tag the dialog fragment.  */
    private var mFragmentId: String? = null

    /**
     * Interface to communicate back to the container of this fragment.
     */
    interface CustomDialogFragmentCallback {
        /**
         * When the allow button is clicked the container activity is called. finish() should be called in this callback to close the transparent activity.
         */
        fun onDismiss()

        /**
         * The AlertDialog to use to build the dialog.. This cannot be passed as parameter to a constructor because when rotating the device Android expects not
         * to have constructors where you could pass this.
         *
         * @return The AlertDialog to use to build the dialog.
         */
        val alertDialog: AlertDialog?
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CustomDialogFragmentCallback) {
            mCallback =
                context
        }
    }

    /**
     * Shows the dialog.
     *
     * @param fragmentManager The fragment manager of the activity needed to show this dialog.
     */
    fun show(fragmentManager: FragmentManager?) {
        super.show(fragmentManager!!, mFragmentId)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AppCompatDialog {
        mFragmentId = arguments!!.getString("id")
        if (mCallback != null) {
            mAlertDialog = mCallback!!.alertDialog
        }
        //mAlertDialog.setCanceledOnTouchOutside(false);
        return mAlertDialog!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // SMSECAND-1467: Remove custom DialogTheme
        // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (mCallback != null) {
            mCallback!!.onDismiss()
        }
    }

    companion object {
        /**
         * Static constructor of this class.
         *
         * @param id The Id to be used to tag the dialog fragment.
         * @return An instance of a DialogFragment.
         */
        fun newInstance(id: String?): CustomDialog {
            val f = CustomDialog()
            val args = Bundle()
            args.putString("id", id)
            f.arguments = args
            return f
        }
    }
}