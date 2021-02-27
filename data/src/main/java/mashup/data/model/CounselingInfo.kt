package mashup.data.model


import com.google.gson.annotations.SerializedName

data class CounselingInfo(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("emotion")
    val emotion: Emotion?,
    @SerializedName("distanceKilometer")
    val distanceKilometer: Int?
) {
    data class User(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("nickname")
        val nickname: String?,
        @SerializedName("birthday")
        val birthday: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("locaton")
        val locaton: Locaton?
    ) {
        data class Locaton(
            @SerializedName("latitude")
            val latitude: Double?,
            @SerializedName("longitude")
            val longitude: Double?
        )
    }

    data class Category(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("imageUrl")
        val imageUrl: String?
    )

    data class Emotion(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("imageUrl")
        val imageUrl: String?
    )
}