package mashup.mac.model

import mashup.data.model.EmotionModel

data class CounselingMapModel(
    val id: Int = 1,
    val title: String = "제 남친이 좀 이상...",
    val content: String = "제 남친이 좀 이상...",
    val category: Category,
    val commentCount: Int = 0,
    val date: String = "2020.02.20 오전 08:00",
    val emotion: EmotionModel?,
    val userId: Int?,
    val select: Boolean = false,
    val distance: Int = 5
)