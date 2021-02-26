package mashup.data.sample.api

import io.reactivex.rxjava3.core.Single
import mashup.data.sample.response.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SampleApi {

    @GET("/")
    fun getRandomUsers(
        @Query("results") results: Int = 10
    ): Single<GithubUserResponse>
}