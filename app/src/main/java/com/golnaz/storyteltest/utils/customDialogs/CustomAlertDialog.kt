package com.golnaz.storyteltest.utils.customDialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.golnaz.storyteltest.R
import java.util.*

@SuppressLint("InflateParams")
class CustomAlertDialog(
    activity: Activity,
    onDialogCallBack: AlertDialogCallback,
    bodyText: String?,
    positiveText: String?,
    negativeText: String?
) :
    AppCompatDialog(activity) {
    init {
        Objects.requireNonNull(window)?.setBackgroundDrawableResource(R.color.backgroundColor)
        val dialogView: View = activity.layoutInflater.inflate(R.layout.dialog_layout, null)
        setContentView(dialogView)
        val title = dialogView.findViewById<TextView>(R.id.dialog_title)
        title.text = bodyText
        val positive = dialogView.findViewById<TextView>(R.id.dialog_positive)
        val negative = dialogView.findViewById<TextView>(R.id.dialog_negative)
        positive.text = positiveText
        negative.text = negativeText
        positive.setOnClickListener {
            onDialogCallBack.onPositive()
            dismiss()
        }
        negative.setOnClickListener {
            onDialogCallBack.onNegative()
            dismiss()
        }
    }
}

interface AlertDialogCallback {
    fun onPositive()
    fun onNegative()
}