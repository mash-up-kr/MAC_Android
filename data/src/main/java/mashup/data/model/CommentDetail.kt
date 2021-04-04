package mashup.data.model


import com.google.gson.annotations.SerializedName

data class CommentDetail(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)