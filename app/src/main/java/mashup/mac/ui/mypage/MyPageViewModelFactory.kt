package mashup.mac.ui.mypage

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import mashup.data.api.CounselingApi

class MyPageViewModelFactory(
    savedStateRegistry: SavedStateRegistryOwner,
    bundle: Bundle?,
    private val counselingApi: CounselingApi
) : AbstractSavedStateViewModelFactory(savedStateRegistry, bundle) {

    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return MyPageViewModel(handle, counselingApi) as T
    }
}