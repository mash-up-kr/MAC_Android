package mashup.data.api

import io.reactivex.rxjava3.core.Single
import mashup.data.model.Token
import mashup.data.model.UserAndToken
import mashup.data.request.LoginRequest
import mashup.data.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    fun postLogin(
        @Header("Authorization") snsToken: String,
        @Body request: LoginRequest
    ): Single<BaseResponse<UserAndToken>>

    @POST("auth/refresh")
    fun postRefreshToken(): Single<BaseResponse<Token>>
}