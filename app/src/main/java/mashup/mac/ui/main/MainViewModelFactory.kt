package mashup.mac.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.data.api.CounselingApi
import mashup.data.repository.CounselingRepository
import mashup.data.sample.repository.SampleRepository

class MainViewModelFactory(
    private val counselingRepository: CounselingRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(counselingRepository) as T
    }
}