package mashup.mac.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("setCircleImageUrl")
fun ImageView.setCircleImageUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().circleCrop())
            .into(this)
    }
}

@BindingAdapter("android:src")
fun ImageView.setDrawable(@DrawableRes drawableRes: Int?) {
    drawableRes?.let {
        setImageDrawable(ContextCompat.getDrawable(context, it))
    }
}