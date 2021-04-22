package mashup.data.api

import io.reactivex.rxjava3.core.Single
import mashup.data.model.Comment
import mashup.data.model.Counseling
import mashup.data.model.CounselingDetail
import mashup.data.request.CommentRequest
import mashup.data.request.CounselingAddRequest
import mashup.data.request.CounselingModifyRequest
import mashup.data.response.BaseResponse
import mashup.data.response.CommentResponse
import mashup.data.response.MyCounselingResponse
import retrofit2.http.*

interface CounselingApi {

    /**
     * 고민
     */
    @POST("counselings")
    fun postCounseling(
        @Body request: CounselingAddRequest
    ): Single<BaseResponse<Counseling>>

    @PUT("counselings/{CounselingQuestionId}")
    fun putCounseling(
        @Path("CounselingQuestionId") diaryId: Int,
        @Body request: CounselingModifyRequest
    ): Single<BaseResponse<Counseling>>

    @DELETE("counselings/{CounselingQuestionId}")
    fun deleteCounseling(
        @Path("CounselingQuestionId") counselingQuestionId: Int
    ): Single<BaseResponse<Any>>

    @GET("counselings")
    fun getCounselings(
        @Query("minKilometer") minKilometer: Int, // 1 km → 1
        @Query("maxKilometer") maxKilometer: Int, // 150 m → 0.15
        @Query("category") category: String? = null,
        @Query("emotion") emotion: String? = null
    ): Single<BaseResponse<List<Counseling>>>

    @GET("counselings/my")
    fun getMyCounselings(): Single<BaseResponse<MyCounselingResponse>>

    @GET("counselings/{counselingQuestionId}")
    fun getCounseling(
        @Path("counselingQuestionId") path: Int,
        @Query("counselingQuestionId") query: Int
    ): Single<BaseResponse<CounselingDetail>>

    /**
     * 답변
     */
    @POST("counselings/{counselingQuestionId}/comments")
    fun postComment(
        @Body request: CommentRequest
    ): Single<BaseResponse<Comment>>

    @PUT("counselings/{CounselingQuestionId}/comments/{commentId}")
    fun putComment(
        @Path("CounselingQuestionId") counselingQuestionId: Int,
        @Path("commentId") commentId: Int,
        @Body request: CommentRequest
    ): Single<BaseResponse<Comment>>

    @DELETE("counselings/{CounselingQuestionId}/comments/{commentId}")
    fun deleteComment(
        @Path("CounselingQuestionId") counselingQuestionId: Int,
        @Path("commentId") commentId: Int
    ): Single<BaseResponse<Any>>

    @GET("counselings/{counselingQuestionId}/comments")
    fun getComments(
        @Path("counselingQuestionId") counselingQuestionId: Int
    ): Single<BaseResponse<CommentResponse>>
}