package mashup.data

import android.util.Log
import mashup.data.pref.PrefUtil
import okhttp3.Interceptor
import okhttp3.Response

//https://github.com/square/okhttp/wiki/Interceptors
class AddHeaderInterceptor(private val refresh: Boolean) : Interceptor {

    private val tag = "AddHeaderInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val token = if (refresh) {
            PrefUtil.get(PrefUtil.PREF_ACCESS_TOKEN, "")
        } else {
            PrefUtil.get(PrefUtil.PREF_REFRESH_TOKEN, "")
        }

        Log.d(tag, "refresh : $refresh, token : $token")

        //val headerToken = "Bearer $token"

        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", token)
                .build()
        )
    }
}