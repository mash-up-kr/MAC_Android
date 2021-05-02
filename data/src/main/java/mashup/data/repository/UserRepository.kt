package mashup.data.repository

import io.reactivex.rxjava3.core.Single
import mashup.data.api.UserApi
import mashup.data.model.Signup
import mashup.data.request.LocationRequest
import mashup.data.response.BaseResponse

class UserRepository(
    private val userApi: UserApi
) {

    fun updateLocation(latitude: Double, longitude: Double): Single<BaseResponse<Signup>> {
        val request = LocationRequest(
            location = LocationRequest.Location(
                latitude = latitude,
                longitude = longitude
            )
        )
        return userApi.putLocation(request)
    }

    fun getLocation(): Single<BaseResponse<String>> {
        return userApi.getUserAddress()
    }

    fun getUser(): Single<BaseResponse<Signup>> {
        return userApi.getUser()
    }
}