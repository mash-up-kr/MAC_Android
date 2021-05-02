package mashup.mac.base

import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import mashup.data.ApiProvider
import mashup.data.api.AuthApi
import mashup.data.pref.PrefUtil
import mashup.mac.MACApplication
import mashup.mac.ext.plusAssign
import mashup.mac.ui.login.LoginActivity
import mashup.mac.util.log.Dlog

abstract class BaseViewModel : ViewModel() {

    private val tokenApi = ApiProvider.provideRefreshApi(AuthApi::class.java)

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun onRefreshToken(func: () -> Unit) {
        compositeDisposable += tokenApi.postRefreshToken()
            .subscribe({
                if (it.isSuccess()) {
                    val accessToken = it.data.accessToken
                    if (TextUtils.isEmpty(accessToken)) {
                        goToLogin()
                        return@subscribe
                    }

                    PrefUtil.put(PrefUtil.PREF_ACCESS_TOKEN, accessToken!!)
                    Dlog.d("refresh token success")
                    func.invoke()
                } else {
                    goToLogin()
                }
            }) {
                goToLogin()
            }
    }

    private fun goToLogin() {
        MACApplication.instance.startActivity(
            Intent(MACApplication.instance, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        )
    }
}