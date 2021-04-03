package mashup.mac.ui.counseling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter

class CounselingWriteViewModelFactory(
    private val animalCategoryAdapter: AnimalCategoryAdapter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CounselingWriteViewModel(animalCategoryAdapter) as T
    }
}