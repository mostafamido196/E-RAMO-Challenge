package com.samy.rick.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import com.samy.rick.di.BaseApp

object Utils {

    fun isInternetAvailable(): Boolean {
        val connectivityManager = BaseApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
    fun showNoInternetDialog(
        context: Context,
        onPositive: () -> Unit,
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("No Internet Connection")
        builder.setMessage("Please check your internet connection and try again.")

        builder.setPositiveButton("Reload") { dialog, _ ->
            dialog.dismiss()  // Close the dialog
            onPositive()  // Call the positive action
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()  // Close the dialog
        }

        builder.setCancelable(false)  // Prevent the dialog from being dismissed by clicking outside
        val dialog = builder.create()
        dialog.show()
    }

}