package mashup.mac.ext

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("android:onSingleClick")
fun View.setOnSingleClick(action: () -> Unit) {
    setOnSingleClickListener {
        action.invoke()
    }
}

fun View.setOnSingleClickListener(action: (v: View) -> Unit) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(view: View) {
            action.invoke(view)
        }
    })
}

fun View.setOnSingleClickListener(action: (v: View) -> Unit, interval: Long) {
    setOnClickListener(object : OnSingleClickListener(interval) {
        override fun onSingleClick(view: View) {
            action.invoke(view)
        }
    })
}

abstract class OnSingleClickListener(
    private val clickDelayMilliSecond: Long = CLICK_DELAY_TIME
) : View.OnClickListener {

    companion object {
        private const val CLICK_DELAY_TIME = 1000L
    }

    private var lastClickTime = 0L

    protected abstract fun onSingleClick(view: View)

    override fun onClick(view: View) {
        val now = System.currentTimeMillis()
        if (now - lastClickTime > clickDelayMilliSecond) {
            onSingleClick(view)
            lastClickTime = now
        }
    }
}