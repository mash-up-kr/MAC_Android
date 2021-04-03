package mashup.mac.ui.counseling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CounselingWriteViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CounselingWriteViewModel() as T
    }
}