package krasnikov.project.pmfightacademy.utils

import android.os.SystemClock
import android.view.View

private const val MIN_CLICK_INTERVAL: Long = 300
private var lastClickTime: Long = 0L

fun View.setSafeOnClickListener(onClick: () -> Unit) {
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime > MIN_CLICK_INTERVAL) {
            onClick()
        }
    }
}
