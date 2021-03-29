package krasnikov.project.pmfightacademy.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

class LoadingErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    @StyleRes
    private val textAppearance: Int

    @DrawableRes
    private val iconError: Int

    @DrawableRes
    private val iconEmpty: Int

    @Dimension
    private val iconSize: Int

    @StyleRes
    private val retryBtnStyle: Int

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        isVisible = false
        isClickable = true

        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.LoadingErrorView, defStyleAttr, 0)

        textAppearance = attributes.getResourceId(
            R.styleable.LoadingErrorView_textAppearance,
            R.style.TextAppearance_AppCompat
        )

        iconError = attributes.getResourceId(
            R.styleable.LoadingErrorView_iconError,
            android.R.drawable.stat_notify_error
        )

        iconEmpty = attributes.getResourceId(
            R.styleable.LoadingErrorView_iconEmpty,
            android.R.color.transparent
        )

        iconSize =
            attributes.getDimension(R.styleable.LoadingErrorView_iconSize, 0f).toInt()

        retryBtnStyle = attributes.getResourceId(
            R.styleable.LoadingErrorView_retryBtnStyle,
            android.R.style.Widget_Material_Button
        )

        attributes.recycle()
    }

    fun showEmpty() {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        removeAllViews()
        addIcon(iconEmpty)
        addText(resources.getString(R.string.title_empty))
    }

    fun showError(message: String, retryAction: () -> Unit) {
        buildErrorView(message, retryAction)
    }

    fun showError(@StringRes resId: Int, retryAction: () -> Unit) {
        buildErrorView(context.getString(resId), retryAction)
    }

    fun showError(message: String) {
        buildErrorView(message)
    }

    fun showError(@StringRes resId: Int) {
        buildErrorView(context.getString(resId))
    }

    fun showLoading() {
        removeAllViews()
        addView(ProgressBar(context))
        isVisible = true
    }

    fun resetState() {
        isVisible = false
        removeAllViews()
    }

    private fun buildErrorView(message: String, retryAction: (() -> Unit)? = null) {
        removeAllViews()
        addIcon(iconError)
        addText(message)

        if (retryAction != null) {
            val retryButton = Button(ContextThemeWrapper(context, retryBtnStyle)).apply {
                setText(R.string.btn_retry)
                setSafeOnClickListener(retryAction)
                layoutParams = LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            addView(retryButton)
        }
        isVisible = true
    }

    private fun addIcon(@DrawableRes id: Int) {
        val icon = AppCompatImageView(context).apply {
            setImageResource(id)
            if (iconSize != 0) {
                minimumHeight = iconSize
                minimumWidth = iconSize
            }
        }
        addView(icon)
    }

    private fun addText(message: String) {
        val textView = AppCompatTextView(context).apply {
            text = message
            gravity = Gravity.CENTER
            setTextAppearance(textAppearance)
        }
        addView(textView)
    }
}
