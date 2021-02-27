package mashup.mac.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override var logTag = "LoginActivity"

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

        viewModel.onClickLogin.observe(this, Observer {
            replaceFragment(SignUpFragment.newInstance())
        })
    }

    private fun replaceFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.layout_frame, fragment)
            addToBackStack(null)
        }.commit()
    }
}