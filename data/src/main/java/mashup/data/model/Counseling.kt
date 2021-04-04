package mashup.data.model


import com.google.gson.annotations.SerializedName

data class Counseling(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("emotionId")
    val emotionId: Int?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("location")
    val location: Location?
)