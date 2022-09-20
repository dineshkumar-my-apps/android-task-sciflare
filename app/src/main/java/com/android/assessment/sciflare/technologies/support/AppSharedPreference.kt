package com.android.assessment.sciflare.technologies.support

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreference {
    private var sharedPreferences: SharedPreferences? = null

    companion object {
        const val PREFS_NAME = "my-task"
    }

    fun putString(context: Context, text: String?, text1: String?) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(text, text1)
        editor.apply()
    }

    fun getString(context: Context, PREFS_KEY: String?): String? {
        val text: String?
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        text = sharedPreferences!!.getString(PREFS_KEY, "")
        return text
    }

    fun removeString(context: Context, PREFS_KEY: String?) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.remove(PREFS_KEY)
        editor.apply()
    }

    fun putInt(context: Context, text: String?, text1: Int) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putInt(text, text1)
        editor.apply()
    }

    fun putstring(context: Context, text: String?, text1: String?) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(text, text1)
        editor.apply()
    }

    fun getInt(context: Context, PREFS_KEY: String?): Int {
        val text: Int
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        text = sharedPreferences!!.getInt(PREFS_KEY, 0)
        return text
    }

    fun removeInt(context: Context, PREFS_KEY: String?) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.remove(PREFS_KEY)
        editor.apply()
    }

    fun putBoolean(context: Context, text: String?, text1: Boolean?) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putBoolean(text, text1!!)
        editor.apply()
    }

    fun getBoolean(context: Context, PREFS_KEY: String?): Boolean? {
        val text: Boolean
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        text = sharedPreferences!!.getBoolean(PREFS_KEY, false)
        return text
    }

    fun removeBoolean(context: Context, PREFS_KEY: String?) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.remove(PREFS_KEY)
        editor.apply()
    }

    fun clearSharedPreference(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }
}