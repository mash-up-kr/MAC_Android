package mashup.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    //TODO type 변수 enum 값으로 변경하기 -> 현재 서버 값 : Point
    @SerializedName("type")
    val type: String?,
    @SerializedName("coordinates")
    val coordinates: Coordinates?
) {
    data class Coordinates(
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?
    )
}