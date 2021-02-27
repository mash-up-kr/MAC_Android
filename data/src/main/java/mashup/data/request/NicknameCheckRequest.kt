package mashup.data.request


import com.google.gson.annotations.SerializedName

data class NicknameCheckRequest(
    @SerializedName("nickname")
    val nickname: String?
)