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
        resetState()
    }

    fun showError(message: String, retryAction: () -> Unit) {
        resetState()
        isClickable = true
        isVisible = true
        tvError.text = message
        tvError.isVisible = true
        btnRetry.isVisible = retryBtnShow
        btnRetry.setOnClickListener { retryAction() }
    }

    fun showError(@StringRes resId: Int, retryAction: () -> Unit) {
        resetState()
        isClickable = true
        isVisible = true
        tvError.text = context.getText(resId)
        tvError.isVisible = true
        btnRetry.isVisible = retryBtnShow
        btnRetry.setOnClickListener { retryAction() }
    }

    fun showError(message: String) {
        resetState()
        isClickable = true
        isVisible = true
        tvError.text = message
        tvError.isVisible = true
    }

    fun showError(@StringRes resId: Int) {
        resetState()
        isClickable = true
        isVisible = true
        tvError.text = context.getText(resId)
        tvError.isVisible = true
    }

    fun showLoading() {
        resetState()
        isClickable = true
        isVisible = true
        progress.isVisible = true
    }

    fun resetState() {
        isVisible = false
        isClickable = false
        tvError.isVisible = false
        btnRetry.isVisible = false
        progress.isVisible = false
        btnRetry.setOnClickListener(null)
    }
}
