package com.samy.rick.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext


sealed class NetworkState {

    //idle
    object Idle : NetworkState()

    //loading
    object Loading : NetworkState()

    //result
    data class Result<T>(var response: T) : NetworkState()

    //error
    data class Error( var msg: String? = null) : NetworkState() {

        fun handleErrors(
            @ApplicationContext
            mContext: Context,
            mDialogsListener: (() -> Unit)? = null
        ) {

            Log.e(TAG, "handleErrors: msg $msg")



        }

        private fun showHelperDialog(
            msg: String,
            mContext: Context,
            mDialogsListener:  (() -> Unit)? = null
        ) {

            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
            if (mDialogsListener != null) {
                mDialogsListener()
            }
        }

        companion object {
            private val TAG = this::class.java.name
        }

    }


}
