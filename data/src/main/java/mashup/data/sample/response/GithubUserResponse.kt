package mashup.data.sample.response

import mashup.data.sample.model.GithubUserModel

data class GithubUserResponse(
    val results: List<GithubUserModel>
)