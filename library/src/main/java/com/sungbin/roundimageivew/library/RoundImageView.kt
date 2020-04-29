package com.sungbin.roundimageivew.library

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundImageView : AppCompatImageView {

    private var radius = dp2px(16)
    private var bitmapRect: RectF? = null
    private var clipPath: Path? = null

    /**
     * Create a new RoundImageView.
     * @param context current activity
     */
    constructor(context: Context) : super(context) {
        RoundImageView(context, null)
    }

    /**
     * Constructor for inflation from XML layout
     * @param context current activity
     * @param attrs provided by layout
     */
    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs){
        val a = context!!.obtainStyledAttributes(attrs,
            R.styleable.RoundImageView,
            0,
            0
        )
        radius = a.getDimensionPixelSize(
            R.styleable.RoundImageView_riv_radius,
            this.radius
        )
        a.recycle()
    }

    /**
     * Set the value of RoundImageView
     * @param radius radius for RoundImageView(required)
     */
    fun set(radius: Int = this.radius) {
        this.radius = radius
        invalidate()
    }

    override fun draw(canvas: Canvas) {
        clipPath = Path()
        clipPath!!.reset()
        if(bitmapRect == null)
            bitmapRect = RectF(0f, 0f,
                width.toFloat(),
                height.toFloat())
        clipPath!!.addRoundRect(
            bitmapRect!!,
            this.radius.toFloat(),
            this.radius.toFloat(),
            Path.Direction.CW
        )
        canvas.clipPath(clipPath!!)
        super.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapRect = RectF(0f, 0f, w.toFloat(), h.toFloat())
    }

    private fun dp2px(dp: Int): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}