package krasnikov.project.pmfightacademy.app.data.exception.pref

import android.content.Context
import android.content.SharedPreferences
import krasnikov.project.pmfightacademy.R

class SharedPref(context: Context) {
    private companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(context.getString(R.string.preference_login_key),
            Context.MODE_PRIVATE
        )
    }

    var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, "")
}