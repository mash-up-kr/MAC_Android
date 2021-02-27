package mashup.data.model


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("counselingQuestionId")
    val counselingQuestionId: Int?
)