package mashup.mac

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import mashup.data.pref.PrefUtil

class MACApplication : Application() {

    companion object {

        lateinit var instance: MACApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        PrefUtil.init(this)
        KakaoSdk.init(this, "5076efb469d1742dd2fb665625c764d7")
    }
}