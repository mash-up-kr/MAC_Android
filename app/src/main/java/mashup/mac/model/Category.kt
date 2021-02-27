package mashup.mac.model

import androidx.annotation.DrawableRes
import mashup.mac.R
import java.util.*

enum class Category(
    val title: String,
    val animalName: String,
    @DrawableRes val bodyRes: Int,
    @DrawableRes val faceRes: Int,
    @DrawableRes val circleRes: Int
) {
    음식("음식", "돼지", R.drawable.body_pig, R.drawable.face_pig, R.drawable.circle_pig),
    관계("관계", "햄스터", R.drawable.body_hamster, R.drawable.face_hamster, R.drawable.circle_hamster),
    직업("직업", "소", R.drawable.body_cow, R.drawable.face_cow, R.drawable.circle_cow),
    기타("기타", "코끼리", R.drawable.body_elephant, R.drawable.face_elephant, R.drawable.circle_elephant),
    연애("연애", "고양이", R.drawable.body_cat, R.drawable.face_cat, R.drawable.circle_cat),
    학업("학업", "원숭이", R.drawable.body_monkey, R.drawable.face_monkey, R.drawable.circle_monkey);

    companion object {
        fun getAllCategories() = values().toList()

        fun getRandomCategory(): Category {
            val allCategories = getAllCategories()
            val index = Random().nextInt(allCategories.size)
            return allCategories[index]
        }
    }
}