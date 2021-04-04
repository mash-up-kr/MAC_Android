package mashup.mac

import android.app.Application
import mashup.data.pref.PrefUtil

class MACApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefUtil.init(this)
    }
}