package mashup.data

import mashup.data.api.CounselingApi
import mashup.data.repository.CounselingRepository

object Injection {

    fun provideCounselingRepository() = CounselingRepository(
        ApiProvider.provideApi(CounselingApi::class.java)
    )
}