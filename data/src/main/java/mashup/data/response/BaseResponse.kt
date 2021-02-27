package mashup.data.response

data class BaseResponse<T>(
    val code: Int = -1,
    val data: T,
    val error: String = ""
)