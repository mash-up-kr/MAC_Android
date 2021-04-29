package mashup.data

import mashup.data.api.CounselingApi
import mashup.data.api.UserApi
import mashup.data.repository.CounselingRepository
import mashup.data.repository.UserRepository

object Injection {

    fun provideCounselingRepository() = CounselingRepository(
        ApiProvider.provideApi(CounselingApi::class.java)
    )

    fun provideUserRepository() = UserRepository(
        ApiProvider.provideApi(UserApi::class.java)
    )
}