package mashup.mac.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override var logTag = "LoginActivity"
    val KAKAO_TAG ="kakaoTag"
    val TAG = "tag"
    private val viewModel by lazy {
        ViewModelProvider(
            this, LoginViewModelFactory(
                SampleInjection.provideRepository()
            )
        ).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginVm = viewModel

//        viewModel.onClickLogin.observe(this, Observer {
//            replaceFragment(SignUpFragment.newInstance())
//        })
        binding.btnLogin.setOnClickListener{
            //공통 Call back
            //loadingDialog.show()
            val TAG = "카카오"

            // 로그인 공통 callback 구성
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if(error!= null){
                    Log.e("LoginTest", "로그인 실패", error)
                }
                else if(token!=null){
                    Log.i("LoginTest", "로그인 성공 ${token.accessToken}")
                    //사용자 정보 가져오기
                    UserApiClient.instance.me { user, error ->
                        if(error!=null){
                            Log.e(KAKAO_TAG, "사용자 정보 요청 실패", error)
                        }
                        else if(user!=null){
                            Log.i(KAKAO_TAG, "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n간편가입 시간: ${user.synchedAt}" +
                                    "\n서비스 연결 완료 시간: ${user.connectedAt}" +
                                    "\nproperties: ${user.properties}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
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

//            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                if (error != null) {
//                    Log.d("check", "${error.message}")
//                } else if (tokenInfo != null) {
//                    intent.putExtra("sort", "kakao")
//                    startActivity(intent)
//                    finish()
//                }
//            }

        }

    }
//
//    private fun replaceFragment(fragment: BaseFragment<*>) {
//        supportFragmentManager.beginTransaction().apply {
//            add(R.id.layout_frame, fragment)
//            addToBackStack(null)
//        }.commit()
//    }

}