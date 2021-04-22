package mashup.data.model

enum class EmotionModel(
    val title: String
) {
    기쁨("기쁨"),
    슬픔("슬픔"),
    화남("화남"),
    당황("당황"),
    짜증("짜증");

    companion object {

        fun getFromItem(title: String) = getAllEmotions().find { it.title == title }

        private fun getAllEmotions() = values().toList()
    }
}
