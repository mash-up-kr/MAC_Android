package mashup.data.request


import com.google.gson.annotations.SerializedName
import mashup.data.model.Location

/**
 * 상담 추가 및 수정 시 인자가 1개만 차이나서 open class 로 분리해 봤는데
 * 서버 완료되면 잘 되는지 확인해봐야 합니다.
 */
open class CounselingRequest(
    @SerializedName("title")
    val _title: String?,
    @SerializedName("content")
    val _content: String?,
    @SerializedName("categoryId")
    val _categoryId: Int?,
    @SerializedName("emotionId")
    val _emotionId: Int?,
)

data class CounselingAddRequest(
    val title: String?,
    val content: String?,
    val categoryId: Int?,
    val emotionId: Int?,

    @SerializedName("location")
    val location: Location?
) : CounselingRequest(_title = title, _content = content, _categoryId = categoryId, _emotionId = emotionId)

data class CounselingModifyRequest(
    val title: String?,
    val content: String?,
    val categoryId: Int?,
    val emotionId: Int?,

    @SerializedName("counselingQuestionId")
    val counselingQuestionId: Int?
) : CounselingRequest(_title = title, _content = content, _categoryId = categoryId, _emotionId = emotionId)
