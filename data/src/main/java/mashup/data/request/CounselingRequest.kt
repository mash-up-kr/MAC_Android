package mashup.data.request


import com.google.gson.annotations.SerializedName

data class CounselingRequest(
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("emotionId")
    val emotionId: Int?,
    @SerializedName("userId")
    val userId: Int?
)