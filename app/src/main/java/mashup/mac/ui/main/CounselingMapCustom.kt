package mashup.mac.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import mashup.mac.R


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
    private val rect = RectF()

    var titleSize = 40F
    var subtileSize = 35F
    var textStarMargin = 30F

    private var cue: List<CounselingMapModel>? = null

    private var width: Float
    private val offset: Int = 120


    init {
        val black: Int = ContextCompat.getColor(context, R.color.black)
        val white: Int = ContextCompat.getColor(context, R.color.white)

        width = (offset * 3.5).toFloat()
        mTitlePaint.color = black
        mTitlePaint.textSize = titleSize
        mTitlePaint.textAlign = Paint.Align.LEFT

        mSubTittlePaint.color = white
        mSubTittlePaint.textSize = subtileSize
        mSubTittlePaint.textAlign = Paint.Align.LEFT

        paint.color = ContextCompat.getColor(context, R.color.point)
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
        cue?.forEach {
            val itemStartX = (it.x - (width * 0.5)).toInt()

            rect.set(
                itemStartX.toFloat() + textStarMargin,
                it.y.toFloat(),
                itemStartX.toFloat() + offset + textStarMargin,
                (it.y + offset).toFloat()
            )

            canvas.drawArc(rect, 0F, 360F, true, paint)

            ContextCompat.getDrawable(context, R.drawable.chat)?.run {
                setBounds(itemStartX, it.y - offset, (itemStartX + width).toInt(), it.y)
                draw(canvas)
            }

            canvas.drawText(
                it.title ,
                itemStartX.toFloat()+textStarMargin,
                (it.y - (offset * 0.5f)),
                mTitlePaint
            )

            canvas.drawText(
                it.category ,
                it.x - textStarMargin,
                (it.y + (offset * 0.6f)),
                mSubTittlePaint
            )

            ContextCompat.getDrawable(context, R.drawable.circle_cat)?.run {
                val imageX = (it.x - (width * 0.5)).toInt()
                setBounds(
                    (imageX +textStarMargin+ 8).toInt(),
                    it.y + 8,
                    (imageX +textStarMargin+ offset - 8).toInt(),
                    it.y + offset - 8
                )
                draw(canvas)
            }

        }
    }
}