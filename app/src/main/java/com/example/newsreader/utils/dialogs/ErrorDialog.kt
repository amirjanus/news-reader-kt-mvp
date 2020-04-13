package com.example.newsreader.utils.dialogs

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.newsreader.R

class ErrorDialog : DialogFragment() {

    companion object {
        fun newInstance(): ErrorDialog {
            val args: Bundle = Bundle()

            val errorDialog: ErrorDialog = ErrorDialog()
            errorDialog.arguments = args

            return errorDialog
        }
    }

    /**
     * Builds ErrorDialog.
     *
     * @param savedInstanceState The last saved instance state of the Fragment or null if this is a
     *                           freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)

        builder.setTitle(R.string.error_dialog_title)
        builder.setMessage(R.string.error_dialog_message)
        builder.setNegativeButton(R.string.error_dialog_button, { _, _ -> dismiss() })

        return builder.create()
    }

}
