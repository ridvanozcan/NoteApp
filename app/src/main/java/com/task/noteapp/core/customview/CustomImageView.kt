package com.task.noteapp.core.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class CustomImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    var radius = 15.0f

    override fun onDraw(canvas: Canvas) {
        @SuppressLint("DrawAllocation") val clipPath = Path()
        @SuppressLint("DrawAllocation") val rect = RectF(
            0F, 0F, this.width.toFloat(),
            this.height.toFloat()
        )
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(clipPath)
        super.onDraw(canvas)
    }
}