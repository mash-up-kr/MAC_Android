package mashup.mac.ui.login

import androidx.lifecycle.MutableLiveData
import mashup.data.sample.repository.SampleRepository
import mashup.mac.base.BaseViewModel

class LoginViewModel(
    private val sampleRepository: SampleRepository
) : BaseViewModel() {

    val onClickLogin = MutableLiveData<Unit>()

    fun onClickLogin() {
        onClickLogin.postValue(Unit)
    }
}
