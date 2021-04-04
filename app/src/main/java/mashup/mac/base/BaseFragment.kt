package mashup.mac.base

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    protected lateinit var binding: B

    abstract var logTag: String

    private val tokenApi = ApiProvider.provideRefreshApi(UserApi::class.java)

    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewModelSetup()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    open fun onViewModelSetup() {}

    override fun onResume() {
        super.onResume()
        Showlog.d(logTag)
    }

    fun onError(throwable: Throwable, func: () -> Unit) {
        if (throwable is Exception.AuthenticationException) {
            val refreshToken = PrefUtil.get(PrefUtil.PREF_REFRESH_TOKEN, "")
            Dlog.d("refreshToken : $refreshToken")

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