package mashup.mac.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import mashup.mac.R
import mashup.mac.model.Category


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

    private var cue: List<CounselingMapModel>? = null

    private val offset: Int = 120


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
        this.cue = cue
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSubtitle(canvas)
    }

    private fun drawSubtitle(canvas: Canvas) {
        canvas.drawCircle(width / 2f, height / 2f, 300F, linePaint)
        canvas.drawCircle(width / 2f, height / 2f, 300F + offset * 1.5f, linePaint)
        canvas.drawCircle(width / 2f, height / 2f, 300F + offset * 3, linePaint)

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