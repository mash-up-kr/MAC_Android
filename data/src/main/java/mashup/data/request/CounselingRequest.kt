package mashup.data.request

import mashup.data.model.CategoryModel
import mashup.data.model.EmotionModel

data class CounselingAddRequest(
    val title: String?,
    val content: String?,
    val category: CategoryModel?,
    val emotion: EmotionModel?,

    val latitude: Double?,
    val longitude: Double?
)

data class CounselingModifyRequest(
    val title: String?,
    val content: String?,
    val category: CategoryModel?,
    val emotion: EmotionModel?,

    val counselingQuestionId: Int?
)
