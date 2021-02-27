package mashup.mac.ui.login

import android.os.Bundle
import android.view.View


import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentSignUpBinding
import mashup.mac.ui.sample.adapter.GithubUserAdapter

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {


    override var logTag = "SampleFragment"

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val loginViewModel by lazy {
        ViewModelProvider(
            viewModelStore, LoginViewModelFactory(
                SampleInjection.provideRepository()
            )
        ).get(LoginViewModel::class.java)
    }

    private val userAdapter by lazy { GithubUserAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginVm = loginViewModel
    }

}