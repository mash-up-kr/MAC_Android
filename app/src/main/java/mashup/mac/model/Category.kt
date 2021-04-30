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
    음식("음식", "뭐든 잘 먹는 돼지야!", R.drawable.body_pig, R.drawable.face_pig, R.drawable.circle_pig),
    관계("관계", "함께 어울리는 햄스터야!", R.drawable.body_hamster, R.drawable.face_hamster, R.drawable.circle_hamster),
    직업("직업", "열심히 사는 소야!", R.drawable.body_cow, R.drawable.face_cow, R.drawable.circle_cow),
    기타("기타", "네가 좋아하는 고민 많은 동물이야!", R.drawable.body_etc, R.drawable.face_etc, R.drawable.circle_etc),
    연애("연애", "사랑에 빠진 고양이야!", R.drawable.body_cat, R.drawable.face_cat, R.drawable.circle_cat),
    학업("학업", "밤새 공부하는 원숭이", R.drawable.body_monkey, R.drawable.face_monkey, R.drawable.circle_monkey);

    companion object {

        fun getFromTitleCategory(title: String?): Category = getAllCategories().find { it.title == title } ?: Category.관계

        fun getFromTitle(title: String?) = getAllCategories().find { it.title == title }

        fun getAllCategories() = values().toList()

        fun getRandomCategory(): Category {
            val allCategories = getAllCategories()
            val index = Random().nextInt(allCategories.size)
            return allCategories[index]
        }

        fun findAnimalName(title:String): String? {
            return getAllCategories().find {
                it.title == title
            }?.let { it.animalName }
        }
        fun findCircleImage(title: String): Int? {
            return getAllCategories().find {
                it.title == title
            }?.let { it.circleRes }
        }

    }
}