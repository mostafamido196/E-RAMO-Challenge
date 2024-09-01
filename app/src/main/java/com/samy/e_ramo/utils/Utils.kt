package com.samy.e_ramo.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import com.samy.e_ramo.di.BaseApp
import java.net.InetAddress

object Utils {
//    fun isInternetAvailable(): Boolean {
//        return try {
//            val ipAddr: InetAddress = InetAddress.getByName("google.com")
//            //You can replace it with your name
//            !ipAddr.equals("")
//        } catch (e: Exception) {
//            false
//        }
//    }
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