package mashup.mac.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.data.api.AuthApi
import mashup.data.api.UserApi
import mashup.data.repository.CounselingRepository
import mashup.data.repository.UserRepository
import mashup.data.sample.repository.SampleRepository

class LoginViewModelFactory(
    private val  userApi: UserApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(userApi) as T
    }
}