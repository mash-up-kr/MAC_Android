package mashup.mac.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.repository.SampleRepository

class MainViewModelFactory(
    private val sampleRepository: SampleRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(sampleRepository) as T
    }
}