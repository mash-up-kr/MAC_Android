package mashup.data.model


import com.google.gson.annotations.SerializedName

data class Signup(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("token")
    val token: Token?,
    @SerializedName("location")
    val location: Location?
)