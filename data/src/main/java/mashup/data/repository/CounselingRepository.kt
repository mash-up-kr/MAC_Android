package mashup.data.repository

import android.util.Log
import io.reactivex.rxjava3.core.Single
import mashup.data.api.CounselingApi
import mashup.data.model.CategoryModel
import mashup.data.model.Counseling
import mashup.data.model.EmotionModel
import mashup.data.request.CounselingAddRequest
import mashup.data.response.BaseResponse

class CounselingRepository(
    private val counselingApi: CounselingApi
) {

    fun addCounseling(
        title: String, content: String, category: String, emotion: String
    ): Single<BaseResponse<Counseling>> {

        val request = CounselingAddRequest(
            title = title,
            content = content,
            category = CategoryModel.getFromTitle(category),
            emotion = EmotionModel.getFromItem(emotion),

            //todo 저장된 위치정보 가져오기 -> 강남 좌표 하드 코딩
            latitude = 49.124214,
            longitude = 4.8148428
        )

        return counselingApi.postCounseling(request)
    }

    fun getCounselingList(
        minKilometer: Double,
        maxKilometer: Double,
        category: String? = null,
        emotion: String? = null
    ): Single<BaseResponse<List<Counseling>>> {
        return counselingApi.getCounselings(minKilometer, maxKilometer, category, emotion)

//        try {
//            val minKilometer = minKilometer.toString()
//            val maxKilometer = maxKilometer.toString()
//
//        } catch (e: Exception) {
//            Log.e("Exception ", e.message.toString())
//            return counselingApi.getCounselings(minKilometer.toString(),
//                maxKilometer.toString(), category, emotion)
//        }
    }
}