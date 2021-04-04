package mashup.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code")
    val result: RESULT,
    val data: T,
    val error: String = ""
)

enum class RESULT(val code: Int) {
    SUCCESS(1), ERROR(0), REFRESH_TOKEN(401)
}