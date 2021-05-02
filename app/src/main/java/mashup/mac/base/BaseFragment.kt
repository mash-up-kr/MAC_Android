package mashup.mac.base

import android.content.Intent
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
import mashup.data.api.AuthApi
import mashup.data.pref.PrefUtil
import mashup.mac.ext.plusAssign
import mashup.mac.ui.login.LoginActivity
import mashup.mac.util.log.Dlog
import mashup.mac.util.log.Showlog

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    protected lateinit var binding: B

    abstract var logTag: String

    private val tokenApi = ApiProvider.provideRefreshApi(AuthApi::class.java)

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
        context?.let { _context ->
            _context.startActivity(
                Intent(_context, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
        }
    }
}