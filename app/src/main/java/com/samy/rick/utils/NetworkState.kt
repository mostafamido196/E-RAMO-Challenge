package com.samy.rick.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext

sealed class DataState<out R> {
    object Idle : DataState<Nothing>()
    data class Result<out T>(val data: T) : DataState<T>()
    data class Error(val msg: String?) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
