package mashup.mac.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import mashup.mac.R
import mashup.mac.model.Category
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class CounselingMapCustom : View {
    constructor (context: Context) : super(context)
    constructor (context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor (context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    companion object {
        private const val TAG = "CounselingMapCustom"
    }

    private val mTitlePaint = Paint()
    private val mSubTittlePaint = Paint()
    private val paint = Paint()
    private val linePaint = Paint()
    private val rect = RectF()

    var titleSize = 40F
    var subtileSize = 35F
    var textStarMargin = 10F

    private var cue: List<CounselingMapDrawModel>? = null

    private val offset: Int = 120
    private var rOffset: Int = 120


    init {
        val textCounselingMsg: Int = ContextCompat.getColor(context, R.color.textCounselingMsg)
        val white: Int = ContextCompat.getColor(context, R.color.white)
        val point: Int = ContextCompat.getColor(context, R.color.point)


        mTitlePaint.color = textCounselingMsg
        mTitlePaint.textSize = titleSize
        mTitlePaint.textAlign = Paint.Align.LEFT

        mSubTittlePaint.color = white
        mSubTittlePaint.textSize = subtileSize
        mSubTittlePaint.textAlign = Paint.Align.LEFT

        paint.color = point

        linePaint.isAntiAlias = true
        linePaint.color = point
        linePaint.alpha = 30
        linePaint.strokeWidth = 2F
        linePaint.style = Paint.Style.STROKE
    }

    fun setCueList(cue: List<CounselingMapModel>) {
        this.cue = cue.map {
            Log.e("123", "x   $it")
            CounselingMapDrawModel(
                id = it.id,
                r = offset + it.location * rOffset,
                degree = getDegree(it.id),
                category = it.category,
                distanceKilometer = it.location
            )
        }

        invalidate()
    }

    private val random = Random()
    private fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    private fun getDegree(id: Int): Int {
//        return 0
        return rand(0, 60) + 72 * (id)
    }

    private fun getTargetX(degree: Int, r: Int): Int {
        Log.e("123", "x   $r")

        return (cos(Math.toRadians(degree.toDouble())) * r).toInt()
    }

    private fun getTargetY(degree: Int, r: Int): Int {
        Log.e("123", "y   $r")
        return (sin(Math.toRadians(degree.toDouble())) * r).toInt()
    }

    override fun onMeasure(widthwidthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = (widthwidthMeasureSpec*1.5).toInt()
//        rOffset = (width - widthwidthMeasureSpec)/8
        Log.e("onMeasure", "widthMeasureSpec   $widthwidthMeasureSpec")
        Log.e("onMeasure", "width   $width")

        super.onMeasure(width, heightMeasureSpec)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSubtitle(canvas)
    }

    private fun drawSubtitle(canvas: Canvas) {
//        val radius = 300f
        val radius = offset * 3.toFloat()
        canvas.drawCircle(width / 2f, height / 2f, radius, linePaint)
        canvas.drawCircle(width / 2f, height / 2f, radius + offset * 1.5f, linePaint)
        canvas.drawCircle(width / 2f, height / 2f, radius + offset * 3, linePaint)
        Log.e("123", "width   $width")



        cue?.forEach {
            Log.e("x", "it  $it")
            val x = getTargetX(it.degree, it.r)
            val y = getTargetY(it.degree,  it.r)

            val drawX = x + width / 2 - offset / 2
            val drawY = y + height / 2 + offset / 2
            val itemStartX = (drawX - textStarMargin.toInt())
            rect.set(
                itemStartX.toFloat() + textStarMargin,
                drawY.toFloat() - offset,
                itemStartX.toFloat() + offset + textStarMargin,
                (drawY).toFloat()
            )

            canvas.drawArc(rect, 0F, 360F, true, paint)

            canvas.drawText(
                it.category + " | " + it.distanceKilometer + "Km",
                drawX - textStarMargin,
                (drawY + (offset * 0.5f)),
                mSubTittlePaint
            )

            val img = Category.findCircleImage(it.category) ?: R.drawable.circle_cat
            val imgMargin = 5
            ContextCompat.getDrawable(context, img)?.run {
                val imageX = (drawX - textStarMargin).toInt()
                setBounds(
                    (imageX + textStarMargin + imgMargin).toInt(),
                    drawY - offset + imgMargin,
                    (imageX + textStarMargin + offset - imgMargin).toInt(),
                    drawY - imgMargin
                )
                draw(canvas)
            }
            canvas.drawCircle(width / 2f, height / 2f, 20f, linePaint)


        }
    }


}