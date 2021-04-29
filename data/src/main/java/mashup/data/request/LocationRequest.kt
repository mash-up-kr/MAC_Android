package mashup.data.request


import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("location")
    val location: Location

) {
    data class Location(
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?
    )
}
