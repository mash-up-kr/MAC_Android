package mashup.mac.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.ApiProvider
import mashup.data.api.AuthApi
import mashup.data.api.UserApi
import mashup.data.request.LoginRequest
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityLoginBinding
import mashup.mac.ext.toast
import mashup.mac.ui.main.MainActivity
import mashup.mac.util.log.Dlog


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override var logTag = "LoginActivity"

    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore, LoginViewModelFactory(
                ApiProvider.provideApiWithoutHeader(UserApi::class.java)
            )
        ).get(LoginViewModel::class.java)
    }

    private val userApiWithHeader by lazy {
        ApiProvider.provideApi(UserApi::class.java)
    }

    private val authApiWithoutHeader by lazy {
        ApiProvider.provideApiWithoutHeader(AuthApi::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginVm = viewModel

        initButton()
        //TODO checkAutoLogin()
    }

    private fun checkAutoLogin() {
        userApiWithHeader.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Dlog.d(it.data.toString())
                if (it.isSuccess()) {
                    //토큰이 유효하므로 자동 로그인
                    goToMainActivity()
                }
            }) {
                showToast(it.message)
                Dlog.e(it.message)
            }
    }

    private fun initButton() {
        binding.btnLogin.setOnClickListener {
            // 로그인 공통 callback 구성
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LoginTest", "로그인 실패", error)
                } else if (token != null) {
                    Log.i("LoginTest", "로그인 성공 ${token.accessToken}")
                    //사용자 정보 가져오기
                    login(token.accessToken)
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                //기본 웹 브라우저를 통해 카카오계정으로 로그인
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    private fun replaceFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.layout_frame, fragment)
            addToBackStack(null)
        }.commit()
    }


    private fun login(token: String) {
        //kakao sns token
        val snsToken = "Bearer $token"

        Dlog.d("login snsToken : $snsToken")

        authApiWithoutHeader.postLogin(
            snsToken = snsToken,
            LoginRequest(snsType = "kakao")
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Dlog.d(it.toString())

                // 이미 회원인 경우
                if (it.isSuccess()) {

                    val accessToken = it.data.token?.accessToken
                    if (accessToken == null) {
                        showError()
                        return@subscribe
                    }

                    val refreshToken = it.data.token?.refreshToken
                    if (refreshToken == null) {
                        showError()
                        return@subscribe
                    }

                    viewModel.saveToken(accessToken, refreshToken)
                    goToMainActivity()

                } else {
                    // 회원이 아닌 경우
                    replaceFragment(SignUpFragment.newInstance(snsToken))
                }
            }) {
                Dlog.e(it.message)
                showToast(it.message)
            }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showToast(message: String?) {
        message?.let { toast(it) }
    }

    private fun showError() {
        toast("something wrong error")
    }
}