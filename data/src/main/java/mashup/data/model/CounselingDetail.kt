package mashup.data.model


import com.google.gson.annotations.SerializedName

data class CounselingDetail(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("emotion")
    val emotion: Emotion?,
    @SerializedName("distanceKilometer")
    val distanceKilometer: Int?
)