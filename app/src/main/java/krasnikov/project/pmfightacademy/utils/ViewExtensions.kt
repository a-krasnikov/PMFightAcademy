package krasnikov.project.pmfightacademy.utils

import android.os.SystemClock
import android.text.Editable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.google.android.material.internal.TextWatcherAdapter
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.storage.StorageReference

private const val MIN_CLICK_INTERVAL: Long = 300
private var lastClickTime: Long = 0L

fun View.setSafeOnClickListener(onClick: () -> Unit) {
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime > MIN_CLICK_INTERVAL) {
            onClick()
        }
    }
}

fun AppCompatImageView.setImageFromGlide(reference: StorageReference) {
    Glide.with(this).load(reference).into(this)
}
