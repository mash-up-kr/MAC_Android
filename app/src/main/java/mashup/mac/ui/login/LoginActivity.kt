package mashup.mac.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivityLoginBinding
import mashup.mac.databinding.ActivityMainBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override var logTag = "MainActivity"

    //TODO:: ktx 공부하고 적용하기
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
    }
}