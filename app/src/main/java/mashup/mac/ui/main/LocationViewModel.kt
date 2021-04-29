package mashup.mac.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.pref.PrefUtil
import mashup.data.repository.UserRepository
import mashup.mac.base.BaseViewModel
import mashup.mac.ext.EventMutableLiveData
import mashup.mac.ext.plusAssign
import mashup.mac.ext.postEvent
import mashup.mac.util.log.Dlog

class LocationViewModel(
    private val activity: Activity,
    private val userRepository: UserRepository
) : BaseViewModel() {

    companion object {

        private const val PERMISSION_ACCESS_FINE_LOCATION = 1000

        private const val REQUEST_ACCURACY_LOCATION = 1001
    }

    val eventShowToast = EventMutableLiveData<String>()

    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String> get() = _locationName

    fun checkLocationFirstTime() {
        val latitude = PrefUtil.get(PrefUtil.PREF_USER_LATITUDE, "")
        val longitude = PrefUtil.get(PrefUtil.PREF_USER_LONGITUDE, "")
        Dlog.d("latitude : $latitude, longitude : $longitude")

        if (latitude.isEmpty() || longitude.isEmpty()) {
            checkLocationPermission()
        } else {
            showUserLocation(latitude = latitude.toDoubleOrNull(), longitude = longitude.toDoubleOrNull())
        }
    }

    /**
     * [start] 위치 권한 받아오기
     */
    fun checkLocationPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            // 권한 없음
            Dlog.d("권한 없음")
            requestPermission()
        } else {
            // 권한이 이미 있음.
            Dlog.d("권한이 이미 있음")
            checkGpsSettingChange()
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // grantResults[0] 거부 -> -1
        // grantResults[0] 허용 -> 0
        Dlog.d("requestCode : $requestCode")
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // ACCESS_FINE_LOCATION 에 대한 권한 획득.
            Dlog.d("권한 획득")
            if (requestCode == PERMISSION_ACCESS_FINE_LOCATION) {
                checkGpsSettingChange()
            }
        } else {
            // ACCESS_FINE_LOCATION 에 대한 권한 거부.
            Dlog.d("권한 거부")
        }
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Dlog.d("onActivityResult requestCode : $requestCode, resultCode : $resultCode")
        if (requestCode == REQUEST_ACCURACY_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                startLocationUpdates()
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ACCESS_FINE_LOCATION
        )
    }

    /**
     * <GPS 설정 변경 요청>
     * - 높은 정확성(GPS, Wifi, 블루투스, 이동통신망)
     * - 배터리 절약(Wifi, 블루투스, 이동통신망)
     * - 기기 전용(GPS만 이용)
     *
     * -> 확인을 누르면 위치 서비스 사용은 자동으로 On 됩니다.
     *
     * https://woochan-dev.tistory.com/52
     */
    private fun checkGpsSettingChange() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val result = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)// 설정이 안되어있는경우 catch로 빠짐.

                //이미 설정되어있는 경우 catch로 빠지지 않음.
                Dlog.d("checkGpsSettingChange startConfirmLogic")
                startLocationUpdates()
            } catch (e: ApiException) {
                Dlog.d("checkGpsSettingChange $e")
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolvable = e as ResolvableApiException
                        //onActivityResult 실행
                        resolvable.startResolutionForResult(activity, REQUEST_ACCURACY_LOCATION)
                    }
                }
            }
        }
    }
    /**
     * [end] 위치 권한 받아오기
     */


    /**
     * [start] 실시간 위치정보
     */
    //https://developer.android.com/training/location/request-updates
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(activity) }

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            interval = 1000
            //fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                for (location in locationResult.locations) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    Dlog.d("locationCallback latitude : $latitude , longitude : $longitude")
                    updateUserLocation(
                        latitude = latitude,
                        longitude = longitude
                    )
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        Dlog.d("startLocationUpdates")
        stopLocationUpdates()
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        Dlog.d("stopLocationUpdates")
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    /**
     * [end] 실시간 위치정보
     */

    private fun updateUserLocation(latitude: Double, longitude: Double) {
        compositeDisposable += userRepository.updateLocation(
            latitude = latitude,
            longitude = longitude
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccess()) {
                    Dlog.d("success : " + it.data.toString())
                    saveUserLocationToPref(latitude = latitude, longitude = longitude)
                    showUserLocation(latitude = latitude, longitude = longitude)
                    stopLocationUpdates()
                } else {
                    showToast(it.error)
                }
            }) {
                Dlog.e(it.message)
            }
    }

    private fun showToast(message: String?) {
        message?.let {
            eventShowToast.postEvent(it)
        }
    }

    private fun saveUserLocationToPref(latitude: Double, longitude: Double) {
        PrefUtil.put(PrefUtil.PREF_USER_LATITUDE, latitude.toString())
        PrefUtil.put(PrefUtil.PREF_USER_LONGITUDE, longitude.toString())
    }

    private fun showUserLocation(latitude: Double?, longitude: Double?) {
        //TODO 좌표를 가지고 지역명 가져오기
        _locationName.postValue("$latitude, $longitude")
    }
}