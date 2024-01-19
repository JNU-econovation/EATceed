package com.gaebaljip.exceed.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Preference(context: Context) {
    private val prefName = "Pref"
    private val prefs = context.getSharedPreferences(prefName, MODE_PRIVATE)

    var token:String?
        get() = prefs.getString("token", null)
        set(value) {
            prefs.edit().putString("token", value).apply()
        }
}