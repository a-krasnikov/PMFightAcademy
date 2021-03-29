package krasnikov.project.pmfightacademy.app.data.pref

import android.content.Context
import android.content.SharedPreferences
import krasnikov.project.pmfightacademy.R

class SharedPref(context: Context) {
    companion object {
        private const val KEY_TOKEN = "KEY_TOKEN"
        const val TOKEN_DEFAULT_VALUE = "NoPref"

        private const val KEY_NOTIFICATIONS = "KEY_NOTIFICATIONS"
        const val NOTIFICATIONS_DISABLED = false
        const val NOTIFICATIONS_ENABLED = true
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(context.getString(R.string.preference_login_key),
            Context.MODE_PRIVATE
        )
    }

    var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, TOKEN_DEFAULT_VALUE)
    var notificationState: Boolean by SharedPrefDelegate(sharedPreferences, KEY_NOTIFICATIONS, NOTIFICATIONS_DISABLED)
}
