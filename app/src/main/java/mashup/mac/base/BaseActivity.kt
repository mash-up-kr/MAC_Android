package mashup.mac.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import mashup.data.ApiProvider
import mashup.data.api.UserApi
import mashup.data.exception.Exception
import mashup.data.pref.PrefUtil
import mashup.data.response.RESULT
import mashup.mac.ext.plusAssign
import mashup.mac.ext.toast
import mashup.mac.util.log.Dlog
import mashup.mac.util.log.Showlog

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B

    abstract var logTag: String

    private val tokenApi = ApiProvider.provideRefreshApi(UserApi::class.java)

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenOrientationPortrait()
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        onViewModelSetup()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun setScreenOrientationPortrait() {
        try {
            //https://gun0912.tistory.com/79
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } catch (ignore: IllegalStateException) {
            Dlog.e(ignore.message)
        }
    }

    open fun onViewModelSetup() {}

    override fun onResume() {
        super.onResume()
        Showlog.d(logTag)
    }

    fun onError(throwable: Throwable, func: () -> Unit) {
        if (throwable is Exception.AuthenticationException) {
            compositeDisposable += tokenApi.postRefreshToken()
                .subscribe({
                    if (it.result == RESULT.SUCCESS) {
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
        } else {
            Dlog.e(throwable.message)
            toast(throwable.message)
        }
    }

    private fun goToLogin() {
        //TODO 토큰 만료로 인한 로그인 화면 이동
    }
}
