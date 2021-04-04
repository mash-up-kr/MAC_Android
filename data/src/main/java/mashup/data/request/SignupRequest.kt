package mashup.data.request


import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("snsType")
    val snsType: String?, // google, kakao
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("birthdayYear")
    val birthdayYear: Int?,
    @SerializedName("gender")
    val gender: String? // M, F
)