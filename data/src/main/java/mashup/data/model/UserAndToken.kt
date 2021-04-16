package mashup.data.model


import com.google.gson.annotations.SerializedName

data class UserAndToken(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("snsId")
    val snsId: String?,
    @SerializedName("snsType")
    val snsType: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("Location")
    val location: Location?,
    @SerializedName("token")
    val token: Token?
)