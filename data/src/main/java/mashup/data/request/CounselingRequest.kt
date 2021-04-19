package mashup.data.request

data class CounselingGetRequest(
    val minKilometer: Int,
    val maxKilometer: Int,

    //TODO enum 값으로 변경하기
    val category: String? = null,
    val emotion: String? = null
)

data class CounselingAddRequest(
    val title: String?,
    val content: String?,

    //TODO enum 값으로 변경하기
    val category: String?,
    val emotion: String?,

    val latitude: Double?,
    val longitude: Double?
)

data class CounselingModifyRequest(
    val title: String?,
    val content: String?,

    //TODO enum 값으로 변경하기
    val category: String?,
    val emotion: String?,

    val counselingQuestionId: Int?
)
