package mashup.data.model


import com.google.gson.annotations.SerializedName

data class Counseling(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    //TODO enum 값으로 변경하기
    @SerializedName("category")
    val category: String?,
    //TODO enum 값으로 변경하기
    @SerializedName("emotion")
    val emotion: String?,
    @SerializedName("userId")
    val userId: Int?,
    //TODO 서버 API 형식 변경되어야 합니다.
    @SerializedName("location")
    val location: Location?
)