package mashup.mac.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import mashup.data.ApiProvider
import mashup.data.api.AuthApi
import mashup.data.pref.PrefUtil
import mashup.mac.ext.plusAssign
import mashup.mac.ui.login.LoginActivity
import mashup.mac.util.log.Dlog
import mashup.mac.util.log.Showlog

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B

    abstract var logTag: String

    private val tokenApi = ApiProvider.provideRefreshApi(AuthApi::class.java)

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
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        )
    }
}
