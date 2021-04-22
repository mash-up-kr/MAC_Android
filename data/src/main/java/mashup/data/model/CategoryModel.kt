package mashup.data.model

enum class CategoryModel(
    val title: String
) {
    음식("음식"),
    관계("관계"),
    직업("직업"),
    기타("기타"),
    연애("연애"),
    학업("학업");

    companion object {

        fun getFromTitle(title: String?) = getAllCategories().find { it.title == title }

        private fun getAllCategories() = values().toList()
    }
}
