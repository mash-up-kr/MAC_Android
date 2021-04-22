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
//    private var maxOffset: Int = 1
    private var rOffset: Int = 50
//    private val offset: Int = (width * 0.15).toInt()
//    private val offset: Int = width/0.1


    init {
        val textCounselingMsg: Int = ContextCompat.getColor(context, R.color.textCounselingMsg)
        val white: Int = ContextCompat.getColor(context, R.color.white)
        val point: Int = ContextCompat.getColor(context, R.color.point)

//        maxOffset = (width / 2) * 1.3.toInt()
        rOffset = (width / 2) * 0.2.toInt()

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
            CounselingMapDrawModel(
                id = it.id,
                x = getTargetX(getDegree(id), it.location * rOffset),
                y = getTargetY(getDegree(id), it.location * rOffset),
                category = it.category,
                distanceKilometer = it.location
            )
        }
        invalidate()
    }

    val random = Random()
    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    fun getDegree(id: Int): Int {
        return rand(0, 72) * id
    }

    fun getTargetX(degree: Int, r: Int): Int {
        val x = width / 2f
        Log.e("x","degree$degree  r$r")
        return (x + cos(Math.toRadians(degree.toDouble())) * r).toInt()
    }

    fun getTargetY(degree: Int, r: Int): Int {
        val y = height / 2f
        return (y + sin(Math.toRadians(degree.toDouble())) * r).toInt()
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
        //        canvas.drawCircle(
//            width / 2f,
//            height / 2f,
//            radius + offset + (maxOffset - offset) / 2,
//            linePaint
//        )
//        canvas.drawCircle(width / 2f, height / 2f, radius + maxOffset, linePaint)

        cue?.forEach {
            val itemStartX = (it.x - textStarMargin.toInt())
            rect.set(
                itemStartX.toFloat() + textStarMargin,
                it.y.toFloat() - offset,
                itemStartX.toFloat() + offset + textStarMargin,
                (it.y).toFloat()
            )

            canvas.drawArc(rect, 0F, 360F, true, paint)

            canvas.drawText(
                it.category + " | " + it.distanceKilometer + "Km",
                it.x - textStarMargin,
                (it.y + (offset * 0.5f)),
                mSubTittlePaint
            )

            val img = Category.findCircleImage(it.category) ?: R.drawable.circle_cat
            val imgMargin = 5
            ContextCompat.getDrawable(context, img)?.run {
                val imageX = (it.x - textStarMargin).toInt()
                setBounds(
                    (imageX + textStarMargin + imgMargin).toInt(),
                    it.y - offset + imgMargin,
                    (imageX + textStarMargin + offset - imgMargin).toInt(),
                    it.y - imgMargin
                )
                draw(canvas)
            }

        }
    }
}