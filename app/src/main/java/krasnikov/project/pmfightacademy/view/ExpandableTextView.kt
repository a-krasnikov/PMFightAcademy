package krasnikov.project.pmfightacademy.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import krasnikov.project.pmfightacademy.R

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val rectGradient: Drawable?
    private val rectGradientHeight: Int
        get() = lineHeight * 2
    private val paintTextShowMore: Paint

    var maxLinesCollapsed: Int = DEFAULT_MAX_LINES

    var isExpanded = false
        private set(value) {
            field = value
            maxLines = when (value) {
                true -> Int.MAX_VALUE
                false -> maxLinesCollapsed
            }
        }

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)

        rectGradient = ResourcesCompat.getDrawable(
            resources,
            attributes.getResourceId(
                R.styleable.ExpandableTextView_backgroundShowMore,
                android.R.color.transparent
            ),
            null
        ) ?: throw Resources.NotFoundException()

        paintTextShowMore = Paint().apply {
            color = attributes.getColor(
                R.styleable.ExpandableTextView_textColorShowMore,
                android.R.attr.textColor
            )
            textSize = attributes.getDimension(
                R.styleable.ExpandableTextView_textSizeShowMore,
                paint.textSize
            )
            textAlign = Paint.Align.CENTER
        }

        maxLines = maxLinesCollapsed

        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!isExpanded && lineCount > maxLinesCollapsed) {
            rectGradient?.setBounds(0, height - rectGradientHeight - paddingBottom, width, height)
            rectGradient?.draw(canvas)

            canvas.drawText(
                resources.getString(R.string.title_show_more),
                width / 2f,
                height - paddingBottom.toFloat(),
                paintTextShowMore
            )
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                if (isExpanded) {
                    isExpanded = false
                } else {
                    if (y > height - rectGradientHeight - paddingBottom && y < height) {
                        isExpanded = true
                    }
                }
            }
        }

        performClick()
        return true
    }

    companion object {
        const val DEFAULT_MAX_LINES = 4
    }
}
