package mashup.mac.ui.counseling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.data.repository.CounselingRepository
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter

class CounselingWriteViewModelFactory(
    private val counselingRepository: CounselingRepository,
    private val animalCategoryAdapter: AnimalCategoryAdapter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CounselingWriteViewModel(
            counselingRepository,
            animalCategoryAdapter
        ) as T
    }
}