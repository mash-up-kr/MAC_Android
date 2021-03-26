package mashup.mac.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.repository.SampleRepository

class LoginViewModelFactory(
    private val sampleRepository: SampleRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(sampleRepository) as T
    }
}