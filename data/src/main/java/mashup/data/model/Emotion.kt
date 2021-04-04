package mashup.data.model

import com.google.gson.annotations.SerializedName

data class Emotion(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?
)