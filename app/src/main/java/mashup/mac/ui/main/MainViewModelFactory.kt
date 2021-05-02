package mashup.mac.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.data.repository.CounselingRepository
import mashup.data.repository.UserRepository

class MainViewModelFactory(
    private val counselingRepository: CounselingRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(counselingRepository, userRepository) as T
    }
}