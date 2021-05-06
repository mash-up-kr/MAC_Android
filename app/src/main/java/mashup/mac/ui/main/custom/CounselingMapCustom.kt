package mashup.mac.ui.main.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import mashup.mac.R
import mashup.mac.model.Category
import mashup.mac.model.CounselingMapModel
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

    private val contentTextPaint = Paint()
    private val itemBackground = Paint()
    private val itemSelectBackground = Paint()
    private val linePaint = Paint()
    private val rect = RectF()

    var textSize = 35F
    var textStarMargin = 10F

    private var cue: List<CounselingMapDrawModel>? = null

    private var first: Boolean = true
    private var randomDegree: Int = 0
    private var offset: Int = 150
    private var itemOffest: Int = 60

    private var onMapItemClickListener: OnMapItemClickListener? = null

    init {
        val textCounselingMsg: Int = ContextCompat.getColor(context, R.color.textCounselingMsg)
        val white: Int = ContextCompat.getColor(context, R.color.white)
        val point: Int = ContextCompat.getColor(context, R.color.point)


        contentTextPaint.color = white
        contentTextPaint.textSize = textSize
        contentTextPaint.textAlign = Paint.Align.LEFT

        itemBackground.color = point
        itemSelectBackground.color = white

        linePaint.isAntiAlias = true
        linePaint.color = point
        linePaint.alpha = 30
        linePaint.strokeWidth = 5F
        linePaint.style = Paint.Style.STROKE
    }

    fun setOnMapItemClickListener(onMapItemClickListener: OnMapItemClickListener) {
        this.onMapItemClickListener = onMapItemClickListener
    }

    fun setMapWidth(width: Int) {
        if (width < 1200) {
            contentTextPaint.textSize = 30f
            offset = width / 10
            itemOffest = width / 32
        } else {
            this.itemOffest = width / 30
        }
    }

    fun setCueList(
        cue: List<CounselingMapModel>,
        distanceMin: Double,
        distanceMax: Double
    ) {
        val angle = 360/ 5
        this.cue = cue.map {
            val r = (offset * 3 + ((it.distance - distanceMin)/ (distanceMax-distanceMin) *10) * itemOffest)
            val degree = getDegree(angle)
            CounselingMapDrawModel(
                id = it.id,
                select = it.select,
                x = getTargetX(degree, r),
                y = getTargetY(degree, r),
                r = r,
                degree = degree,
                category = it.category.name,
                distance = it.distance
            )
        }
        invalidate()
    }

    fun selectItemId(id: Int) {
        cue?.forEach {
            if (it.select && it.id == id) {
                onMapItemClickListener?.onClickDouble(id)
            }
            it.select = it.id == id
        }
        invalidate()
    }

    private val random = Random()
    private fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    private fun getDegree(angle:Int): Int {
        randomDegree++
        return rand(0, angle) + angle * (randomDegree)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchedX = event.x - width / 2 + offset / 2
        val touchedY = event.y - height / 2 - offset / 2
        val imgMargin = 5
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                true
            }
            MotionEvent.ACTION_MOVE -> {
                true
            }
            MotionEvent.ACTION_UP -> {
                cue?.forEachIndexed { index, it ->
                    if ((it.x + imgMargin) < touchedX &&
                        (it.x + offset - imgMargin) > touchedX &&
                        (it.y - offset + imgMargin) < touchedY &&
                        (it.y + (offset)) > touchedY
                    ) {
                        selectItemId(it.id)
                        onMapItemClickListener?.onClick(index)
                    }
                }
                false
            }
            else -> {
                false
            }
        }
    }

    override fun onMeasure(widthwidthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = if (first) (widthwidthMeasureSpec * 1.5).toInt() else widthwidthMeasureSpec
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
            val drawX = it.x + width / 2 - offset / 2
            val drawY = it.y + height / 2 + offset / 2
            val itemStartX = (drawX - textStarMargin.toInt())

            val select = if (it.select) 8 else 0
            rect.set(
                itemStartX.toFloat() + textStarMargin - select,
                drawY.toFloat() - offset - select,
                itemStartX.toFloat() + offset + textStarMargin + select,
                (drawY).toFloat() + select
            )
            if (it.select)
                canvas.drawArc(rect, 0F, 360F, true, itemSelectBackground)
            else
                canvas.drawArc(rect, 0F, 360F, true, itemBackground)

            canvas.drawText(
                it.category + " | " + it.distance + "Km",
                drawX - textStarMargin,
                (drawY + (offset * 0.5f)),
                contentTextPaint
            )

            val img = Category.findCircleImage(it.category) ?: R.drawable.circle_monkey
            val imgMargin = 5
            ContextCompat.getDrawable(context, img)?.run {
                setBounds(
                    drawX + imgMargin,
                    drawY - offset + imgMargin,
                    drawX + offset - imgMargin,
                    drawY - imgMargin
                )
                draw(canvas)
            }
        }
    }

//    var delayDegree = 0
//    suspend fun cycle() {
//        for (i in 0..1000) {
//            coroutineScope {
//                delay(50)
//                delayDegree++
//                cue?.forEach {
//                    it.x = getTargetX(it.degree + delayDegree, it.r)
//                    it.y = getTargetY(it.degree + delayDegree, it.r)
//                }
//            }
//            this.post {
//                invalidate()
//            }
//        }
//    }

    private fun getTargetX(degree: Int, r: Double): Int {
        return (cos(Math.toRadians(degree.toDouble())) * r).toInt()
    }

    private fun getTargetY(degree: Int, r: Double): Int {
        return (sin(Math.toRadians(degree.toDouble())) * r).toInt()
    }

    interface OnMapItemClickListener {
        fun onClick(position: Int)
        fun onClickDouble(position: Int)
    }
}