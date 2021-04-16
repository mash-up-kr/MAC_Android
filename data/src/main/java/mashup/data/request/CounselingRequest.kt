package mashup.data.request

data class CounselingAddRequest(
    val title: String?,
    val content: String?,
    val category: String?,
    val emotion: String?,

    val latitude: Double?,
    val longitude: Double?
)

data class CounselingModifyRequest(
    val title: String?,
    val content: String?,
    val category: String?,
    val emotion: String?,

    val counselingQuestionId: Int?
)
