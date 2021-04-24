package mashup.mac.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
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

    private val offset: Int = 150
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
        linePaint.strokeWidth = 5F
        linePaint.style = Paint.Style.STROKE
    }

    fun setCueList(cue: List<CounselingMapModel>) {
        this.cue = cue.map {
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
        return rand(0, 60) + 72 * (id)
    }

    override fun onMeasure(widthwidthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = (widthwidthMeasureSpec*1.5).toInt()
        super.onMeasure(width, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSubtitle(canvas)
    }

    private fun drawSubtitle(canvas: Canvas) {
        val radius = offset * 3.toFloat()
        canvas.drawCircle(width / 2f, height / 2f, radius, linePaint)
        canvas.drawCircle(width / 2f, height / 2f, radius + offset * 1.5f, linePaint)
        canvas.drawCircle(width / 2f, height / 2f, radius + offset * 3, linePaint)

        cue?.forEach {
            val x = getTargetX(it.degree + delayDegree, it.r)
            val y = getTargetY(it.degree + delayDegree, it.r)

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
                (drawY + (offset * 0.3f)),
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
        }
    }

    var delayDegree = 0
    suspend fun cycle() {
        for (i in 0..1000) {
            coroutineScope {
                delay(50)
                delayDegree++
            }
            this.post {
                invalidate()
            }
        }
    }

    private fun getTargetX(degree: Int, r: Int): Int {
        return (cos(Math.toRadians(degree.toDouble())) * r).toInt()
    }

    private fun getTargetY(degree: Int, r: Int): Int {
        return (sin(Math.toRadians(degree.toDouble())) * r).toInt()
    }

}