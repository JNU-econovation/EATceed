package com.gaebaljip.exceed

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.gaebaljip.exceed.util.Preference

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        prefs = Preference(applicationContext)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
        //Application에서 가장 오래 존재하기에 릭이 안생길 것이라 추론
        lateinit var prefs: Preference
    }
}