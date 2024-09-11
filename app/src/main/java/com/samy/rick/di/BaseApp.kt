package com.samy.rick.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApp: Application(){
    companion object {
        private lateinit var instance: BaseApp

        fun getInstance(): BaseApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}