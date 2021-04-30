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
import mashup.data.pref.PrefUtil
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
    private val KAKAO_TAG = "kakaoTag"
    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore, LoginViewModelFactory(
                ApiProvider.provideApiWithoutHeader(UserApi::class.java)
            )
        ).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginVm = viewModel
        val userApi = ApiProvider.provideApiWithoutHeader(UserApi::class.java)
        userApi.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccess()){
                    goToMainActivity()
                }
            }) {
                Dlog.e(it.message)
            }

        binding.btnLogin.setOnClickListener {

            val TAG = "카카오"
            // 로그인 공통 callback 구성
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LoginTest", "로그인 실패", error)
                } else if (token != null) {
                    Log.i("LoginTest", "로그인 성공 ${token.accessToken}")
                    //사용자 정보 가져오기
                    testLogin(token.accessToken)

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(KAKAO_TAG, "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.i(KAKAO_TAG, "사용자 정보 요청 성공")
                        }
                    }
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

    private val authApi = ApiProvider.provideApiWithoutHeader(AuthApi::class.java)

    private fun testLogin(token: String) {
        //kakao sns token
        val token = "AZ8cPSyjXl537ERyHZhgiP5Vr5qgSRDvzs5BSgo9dJcAAAF5JMdt-w"
        val snsToken = "Bearer $token"
        authApi.postLogin(
            snsToken = snsToken,
            LoginRequest(snsType = "kakao")
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Dlog.d(it.toString())

                if(it.code == 0){
                    replaceFragment(SignUpFragment.newInstance(snsToken))
                }
                else if (it.isSuccess()) {

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

                    saveToken(accessToken, refreshToken)
                    goToMainActivity()
                } else {
                    showError()
                }
            }) {
                Dlog.e(it.message)
            }
    }

    private fun saveToken(accessToken: String, refreshToken: String) {
        PrefUtil.put(PrefUtil.PREF_ACCESS_TOKEN, accessToken)
        PrefUtil.put(PrefUtil.PREF_REFRESH_TOKEN, refreshToken)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError() {
        toast("something wrong error")
    }

    /**
     * kakao HashKey 값 얻을 때 쓰기!
     **/
/*  private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }*/

}