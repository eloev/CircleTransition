package com.yelloyew.circletransition

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import kotlin.math.pow
import kotlin.math.sqrt

class WhatDraw : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var center: Pair<Float, Float>? = null  // first value - X, second - Y
    private var circleRadius = 10f
    private var color: Int = Color.BLACK

    private val animDuration = 1000L

    private val paint by lazy {
        Paint().apply {
            color = this@WhatDraw.color
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        center?.let { center ->
//            canvas?.drawPaint(paint)
//            paint.color = Color.CYAN

            canvas?.drawCircle(
                center.first,
                center.second,
                circleRadius,
                paint
            )
        }
    }

    fun start(
        containerView: ViewGroup,
        animatedView: View,
        doOnEnd: () -> Unit = {},
        radius: Float = 10f,
        color: Int? = null
    ) {


        val animatedViewCenter = Pair(
            animatedView.x + (animatedView.width / 2f),
            animatedView.y + (animatedView.height / 2f)
        )

        center = animatedViewCenter
        circleRadius = radius
        color?.let { this.color = it }

        val containerCenter: Pair<Float, Float> = Pair(
            containerView.x + (containerView.width / 2f),
            containerView.y + (containerView.height / 2f)
        )   // first value - X, second - Y


        /*
        * scaleTo is a coefficient based on hypotenuse of legs Axis X and Y divided by radius of element.
        * Since this is the hypotenuse, this ratio,
        * regardless of the position of the element being animated,
        * is guaranteed to cover the entire screen at the end of the animation.
        * */

        try {
            (sqrt(
                (containerView.x + containerView.width).toDouble().pow(2.0) +
                        (containerView.y + containerView.height).toDouble().pow(2.0)
            ) / radius)
                .toFloat()
                .also { scaleTo ->
                    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", scaleTo)
                    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", scaleTo)

                    val axisX = (containerCenter.first - animatedViewCenter.first) * scaleTo
                    val translateX = ObjectAnimator.ofFloat(this, "x", axisX)

                    val axisY = (containerCenter.second - animatedViewCenter.second) * scaleTo
                    val translateY = ObjectAnimator.ofFloat(this, "y", axisY)

                    AnimatorSet().apply {
                        duration = animDuration
                        playTogether(scaleX, scaleY, translateX, translateY)
                        start()
                    }.doOnEnd {
                        doOnEnd.invoke()
                        this.visibility = GONE
                    }
                }

            this.visibility = VISIBLE
            this.elevation = 50f
        } catch (e: Exception) {
            doOnEnd.invoke()
        }
    }
}
