package mashup.mac.ui.mypage

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import mashup.data.api.CounselingApi

class MyPageViewModelFactory(
    savedStateRegistry: SavedStateRegistryOwner,
    private val counselingApi: CounselingApi
) : AbstractSavedStateViewModelFactory(savedStateRegistry, null) {

    /*override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyPageViewModel(handle,counselingApi) as T
    }*/

    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return MyPageViewModel(handle, counselingApi) as T
    }
}