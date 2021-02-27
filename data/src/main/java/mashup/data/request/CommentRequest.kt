package mashup.data.request


import com.google.gson.annotations.SerializedName

data class CommentRequest(
    @SerializedName("comment")
    val comment: String?
)