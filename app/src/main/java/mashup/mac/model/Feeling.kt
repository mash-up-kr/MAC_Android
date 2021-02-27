package mashup.mac.model

import androidx.annotation.DrawableRes
import mashup.mac.R

enum class Feeling(
    val title: String,
    @DrawableRes val icon: Int
) {
    기쁨("기쁨", R.drawable.feeling_happy),
    슬픔("슬픔", R.drawable.feeling_sad),
    화남("화남", R.drawable.feeling_angry),
    당황("당황", R.drawable.feeling_embarrassed),
    짜증("짜증", R.drawable.feeling_irritation);

    companion object {
        fun getAllFeeling() = values().toList()
    }
}