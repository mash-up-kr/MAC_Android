package mashup.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("type")
    val type: LocationType?,
    @SerializedName("coordinates")
    val coordinates: Coordinates?
) {
    data class Coordinates(
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?
    )

    enum class LocationType {
        Point
    }
}


