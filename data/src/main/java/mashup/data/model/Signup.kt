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
    @SerializedName("locaton")
    val locaton: Locaton?
) {
    data class Token(
        @SerializedName("accessToken")
        val accessToken: String?,
        @SerializedName("refreshToken")
        val refreshToken: String?
    )

    data class Locaton(
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?
    )
}