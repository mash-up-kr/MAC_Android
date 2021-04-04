package mashup.data.exception

sealed class Exception(message: String) : Throwable(message) {

    class MACHttpException(message: String, code: Int) : Exception(message)

    class AuthenticationException(message: String, code: Int) : Exception(message)
}