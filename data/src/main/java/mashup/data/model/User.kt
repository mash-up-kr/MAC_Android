package mashup.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("location")
    val location: Location?
)