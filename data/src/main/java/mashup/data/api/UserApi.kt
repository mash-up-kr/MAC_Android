package mashup.data.api

import io.reactivex.rxjava3.core.Single
import mashup.data.model.Signup
import mashup.data.model.Token
import mashup.data.request.LocationRequest
import mashup.data.request.NicknameCheckRequest
import mashup.data.request.SignupRequest
import mashup.data.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @POST("users/signup")
    fun postSignup(
        @Body request: SignupRequest
    ): Single<BaseResponse<Signup>>

    @POST("users/signin")
    fun postSignin(
        @Body request: SignupRequest
    ): Single<BaseResponse<Signup>>

    @GET("users/me")
    fun getUser(): Single<BaseResponse<Signup>>

    @PUT("users/me")
    fun putLocation(
        @Body request: LocationRequest
    ): Single<BaseResponse<Signup>>

    @POST("users/refresh")
    fun postRefreshToken(): Single<BaseResponse<Token>>

    @POST("users/nickname/check")
    fun postRefreshToken(
        @Body request: NicknameCheckRequest
    ): Single<BaseResponse<Any>>
}