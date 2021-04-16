package mashup.data.response

data class BaseResponse<T>(
    val code: Int,
    val data: T,
    val error: String = ""
) {
    fun isSuccess() = code == RESULT.SUCCESS.code
    fun isRefreshToken() = code == RESULT.REFRESH_TOKEN.code
}

enum class RESULT(val code: Int) {
    SUCCESS(1), ERROR(0), REFRESH_TOKEN(401)
}