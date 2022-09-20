package com.android.assessment.sciflare.technologies.support

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.widget.TextView
import android.widget.Toast
import com.android.assessment.sciflare.technologies.R

object AppUtils {

    lateinit var loadingDialog: Dialog

    fun loading(context: Context, title: String, boolean: Boolean): Dialog {
        loadingDialog = Dialog(
                context,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        )
        loadingDialog.setContentView(R.layout.layout_loading_progress)
        if (loadingDialog.window != null) {
            loadingDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val textViewTitle: TextView = loadingDialog.findViewById(R.id.title)
        textViewTitle.text = title.toString()
        loadingDialog.setCancelable(boolean)
        loadingDialog.setCanceledOnTouchOutside(boolean)
        return loadingDialog
    }

    fun toastNormal(context: Context?, str: String) {
        Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show()
    }

    fun checkNetworkAvailable(context: Context?): Boolean {
        val connec = context
                ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connec.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}