package mashup.mac.ui.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.repository.SampleRepository

class SampleViewModelFactory(
    private val sampleRepository: SampleRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SampleViewModel(sampleRepository) as T
    }
}