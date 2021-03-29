package krasnikov.project.pmfightacademy.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import krasnikov.project.pmfightacademy.R

class LoadingErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val tvError: TextView by lazy { findViewById(R.id.tvError) }
    private val progress: ProgressBar by lazy { findViewById(R.id.progress) }
    private val btnRetry: Button by lazy { findViewById(R.id.btnRetry) }

    private val retryBtnShow: Boolean

    init {
        inflate(context, R.layout.loading_error_view, this)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        isVisible = false
        isClickable = true

        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.LoadingErrorView, defStyleAttr, 0)

        retryBtnShow = attributes.getBoolean(R.styleable.LoadingErrorView_retryBtnShow, true)
        tvError.setTextColor(
            attributes.getColor(
                R.styleable.LoadingErrorView_textErrorColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
        )
        tvError.setTextAppearance(
            attributes.getResourceId(
                R.styleable.LoadingErrorView_textErrorAppearance,
                R.style.TextAppearance_AppCompat
            )
        )

        attributes.recycle()
    }

    fun showError(message: String, retryAction: () -> Unit) {
        tvError.text = message
        btnRetry.isVisible = retryBtnShow
        btnRetry.setOnClickListener { retryAction() }
        showError()
    }

    fun showError(@StringRes resId: Int, retryAction: () -> Unit) {
        tvError.text = context.getText(resId)
        btnRetry.isVisible = retryBtnShow
        btnRetry.setOnClickListener { retryAction() }
        showError()
    }

    fun showError(message: String) {
        tvError.text = message
        showError()
    }

    fun showError(@StringRes resId: Int) {
        tvError.text = context.getText(resId)
        showError()
    }

    fun showLoading() {
        tvError.isVisible = false
        btnRetry.isVisible = false
        progress.isVisible = true
        isVisible = true
    }

    fun resetState() {
        isVisible = false
        btnRetry.setOnClickListener(null)
    }

    private fun showError() {
        progress.isVisible = false
        tvError.isVisible = true
        isVisible = true
    }
}
