package mashup.data.sample.repository

import io.reactivex.rxjava3.core.Single
import mashup.data.sample.api.SampleApi
import mashup.data.sample.response.GithubUserResponse

class SampleRepository(
    private val sampleApi: SampleApi
) {

    fun getRandomUsers(): Single<GithubUserResponse> {
        return sampleApi.getRandomUsers()
    }
}