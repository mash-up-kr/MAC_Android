package mashup.data.model


import com.google.gson.annotations.SerializedName

data class Counseling(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("commentCount")
    val commentCount: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("category")
    val category: CategoryModel?,
    @SerializedName("emotion")
    val emotion: EmotionModel?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("distance")
    val distance: Int?
)
